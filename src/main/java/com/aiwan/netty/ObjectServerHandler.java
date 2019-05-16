package com.aiwan.netty;

import com.aiwan.publicsystem.DecodeData;
import com.aiwan.util.GetBean;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 暂无用处理器
 * */
@Component("objectServerHandler")
public class ObjectServerHandler extends ChannelInboundHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(ObjectServerHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg+"hhhhhhhhhasdhashdsahd");
        DecodeData decodeData = (DecodeData) msg;
        logger.debug(decodeData.getLength()+"");
        DecodeData decodeData1 = GetBean.getTaskDispatcher().dispatcherTask(decodeData);
        Channel channel = ctx.channel();
        if (decodeData1 == null){
            logger.debug("decodeData1 is null");
        }
        channel.writeAndFlush(decodeData1);
        System.out.println(msg);
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
//        super.exceptionCaught(ctx, cause);
    }
}
