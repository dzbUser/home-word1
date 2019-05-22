package com.aiwan.netty;

import com.aiwan.publicsystem.common.Session;
import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.publicsystem.service.SessionManager;
import com.aiwan.user.entity.User;
import com.aiwan.util.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 处理器
 * */
public class Handler extends SimpleChannelInboundHandler<DecodeData> {

    Map<String,Channel> channelMap = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(Handler.class);

    //netty处理器，把数据传到任务分配器，获取数据后发送出去
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DecodeData decodeData) throws Exception {
        //不是心跳包
        if (decodeData.getType() != Protocol.HEART){
            GetBean.getTaskDispatcher().dispatcherTask(decodeData,ctx.channel());
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        logger.debug("channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        logger.debug("exceptionCaught");
//        if(null != cause) cause.printStackTrace();
        logger.debug("发生异常，删除缓存");
        Session session = SessionManager.getSessionByHashCode(ctx.channel().hashCode());
        if (session != null){
            User user = session.getUser();
            if (user!=null&&user.getUsername()!=null){//检查是否有用户缓存
                SessionManager.removeSessionByUsername(user.getUsername());
            }
            SessionManager.removeSessionByHashCode(ctx.channel().hashCode());
        }
        if(null != ctx) ctx.close();
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        //session加如管理器
        Session session = new Session();
        session.setChannel(ctx.channel());
        SessionManager.putSessionByHashCode(ctx.channel().hashCode(),session);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        //删除缓存
        Session session = SessionManager.getSessionByHashCode(ctx.channel().hashCode());
        if (session != null){
            User user = session.getUser();
            if (user!=null&&user.getUsername()!=null){//检查是否有用户缓存
                SessionManager.removeSessionByUsername(user.getUsername());
            }
            SessionManager.removeSessionByHashCode(ctx.channel().hashCode());
        }
    }
}
