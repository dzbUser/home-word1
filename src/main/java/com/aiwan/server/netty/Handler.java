package com.aiwan.server.netty;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.account.model.User;
import com.aiwan.server.util.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * 处理器
 * */
public class Handler extends SimpleChannelInboundHandler<DecodeData> {

    Map<String,Channel> channelMap = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(Handler.class);

    /**
     *     netty处理器，把数据传到任务分配器，获取数据后发送出去
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DecodeData decodeData) throws Exception {
        //不是心跳包
        if (decodeData.getType() != Protocol.HEART){
            GetBean.getTaskDispatcher().dispatcherTask(decodeData,ctx.channel());
        }
    }

    /**
     * 读取结束
     * */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * 客户端发送异常，清楚登录状态
     * */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.debug("发生异常，删除缓存");
        Session session = SessionManager.getSessionByHashCode(ctx.channel().hashCode());
        if (session != null){
            User user = session.getUser();
            if (user!=null&&user.getAcountId()!=null){
                 GetBean.getUserService().deleteSave(user.getAcountId());
            }
            SessionManager.removeSessionByHashCode(ctx.channel().hashCode());
        }
        if(null != ctx) {
            ctx.close();
        }
    }

    /**
     * 正式连接
     * */
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        //session加如管理器
        Session session = new Session();
        session.setChannel(ctx.channel());
        SessionManager.putSessionByHashCode(ctx.channel().hashCode(),session);
    }

    /**
     * 断开连接，清楚登录状态
     * */
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        //删除缓存
        Session session = SessionManager.getSessionByHashCode(ctx.channel().hashCode());
        if (session != null){
            User user = session.getUser();
            if (user!=null&&user.getAcountId()!=null){
                //检查是否有用户缓存
//                GetBean.getMapManager().removeUser(user.getMap(),user.getAcountId());
//                SessionManager.removeSessionByUsername(user.getAcountId());
                GetBean.getUserService().deleteSave(user.getAcountId());
            }
            SessionManager.removeSessionByHashCode(ctx.channel().hashCode());
        }
    }
}
