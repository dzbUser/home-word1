package com.aiwan.publicsystem.handler;

import com.aiwan.netty.NettyServer;
import com.aiwan.publicsystem.service.ChannelManager;
import com.aiwan.util.UserCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(MyServerHandler.class);

    //管道中上一个Handler触发的事件
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event =(IdleStateEvent)evt;


        switch (event.state()){
            case READER_IDLE:
                logger.debug(ctx.name()+"未接受信息超过5秒");
                //移除channel管理集合
                ChannelManager.removeChannel(ctx.channel());
                //查看是否有登录缓存
                String username = UserCache.getUsernameByChannel(ctx.channel().hashCode());
                if (username != null && !username.equals("")){//有登录缓存
                    //删除缓存
                    ChannelManager.removeChannel(username);
                    UserCache.removeUserByUsername(username);
                    UserCache.removeUsernameByChannel(ctx.channel().hashCode());
                }
                //断开连接
                ctx.channel().close();
                break;
        }


        ctx.fireChannelRead(evt);
    }
}