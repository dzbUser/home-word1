package com.aiwan.publicsystem.handler;

import com.aiwan.publicsystem.common.Session;
import com.aiwan.publicsystem.service.SessionManager;
import com.aiwan.user.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 心跳检测
 * */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(MyServerHandler.class);

    //管道中上一个Handler触发的事件
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event =(IdleStateEvent)evt;


        switch (event.state()){
            case READER_IDLE:
                logger.debug(ctx.name()+"未接受信息超过5秒");
                //删除缓存
                Session session = SessionManager.getSessionByHashCode(ctx.channel().hashCode());
                User user = session.getUser();
                if (user!=null&&user.getUsername()!=null){//检查是否有用户缓存
                    SessionManager.removeSessionByUsername(user.getUsername());
                }
                SessionManager.removeSessionByHashCode(ctx.channel().hashCode());
                //断开连接
                ctx.channel().close();
                break;
        }


        ctx.fireChannelRead(evt);
    }
}