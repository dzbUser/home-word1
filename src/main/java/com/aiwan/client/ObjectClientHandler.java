package com.aiwan.client;

import com.aiwan.publicsystem.DecodeData;
import com.aiwan.role.protocol.CM_UserMessage;
import com.aiwan.util.DeepClone;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ObjectClientHandler extends ChannelInboundHandlerAdapter {
    private DecodeData getTransportObject(){
        CM_UserMessage userMessage = new CM_UserMessage();
        userMessage.setUsername("testone");
        userMessage.setPassword("1");
        byte[] data  = DeepClone.writeInto(userMessage);
        DecodeData decodeData = new DecodeData();
        decodeData.setData(data);
        decodeData.setType(DecodeData.LOGIN);
        decodeData.setLength(data.length);
        return decodeData;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active");
        super.channelActive(ctx);
        //发送消息给服务端
        ctx.writeAndFlush(getTransportObject());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        System.out.println(cause.getMessage());
        ctx.close();
    }
}
