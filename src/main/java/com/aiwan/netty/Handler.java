package com.aiwan.netty;

import com.aiwan.publicsystem.DecodeData;
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
@Component("handler")
public class Handler extends SimpleChannelInboundHandler<DecodeData> {
    private TaskDispatcher taskDispatcher;

    @Autowired
    public void setTaskDispatcher(TaskDispatcher taskDispatcher){
        this.taskDispatcher = taskDispatcher;
    }

    Logger logger = LoggerFactory.getLogger(Handler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DecodeData decodeData) throws Exception {
        DecodeData decodeData1 = taskDispatcher.dispatcherTask(decodeData);
        Channel channel = ctx.channel();
        if (decodeData1 == null){
            logger.debug("decodeData1 is null");
        }
        channel.writeAndFlush(decodeData1);
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
