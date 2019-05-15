package com.aiwan.client;

import com.aiwan.netty.TaskDispatcher;
import com.aiwan.publicsystem.DecodeData;
import com.aiwan.role.protocol.SM_UserMessage;
import com.aiwan.util.DeepClone;
import com.aiwan.util.Protocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 处理器
 * */
public class ClientHandler extends SimpleChannelInboundHandler<DecodeData> {

    Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    //存登录成功username;
    private static String username;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DecodeData decodeData) throws Exception {
        Channel channel = ctx.channel();
        if (decodeData.getType() == Protocol.LOGINFAIL ||decodeData.getType() == Protocol.REGISTDAIL||decodeData.getType() == Protocol.REGISTSUCCESS){
            String content = (String) DeepClone.restore(decodeData.getData());
           System.out.println(content);
        }
        else if (decodeData.getType() == Protocol.LOGINSUCCESS){
            SM_UserMessage userMessage = (SM_UserMessage) DeepClone.restore(decodeData.getData());
            System.out.println("登录成功");
            LoginUser.username = userMessage.getUsername();
            logger.debug(LoginUser.username+"加入缓存");
        }else if(decodeData.getType() == Protocol.LOGOUTSUCCESS){
            String content = (String) DeepClone.restore(decodeData.getData());
            LoginUser.username = "";
            System.out.println(content);
        }
//        channel.writeAndFlush(decodeData);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.debug("exceptionCaught");
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }
}
