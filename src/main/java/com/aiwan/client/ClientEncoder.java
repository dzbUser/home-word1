package com.aiwan.client;

import com.aiwan.publicsystem.DecodeData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientEncoder extends MessageToByteEncoder<DecodeData> {

    Logger logger = LoggerFactory.getLogger(ClientEncoder.class);
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, DecodeData decodeData, ByteBuf byteBuf) throws Exception {
//        logger.debug("Encoder start");
        byteBuf.writeShort(decodeData.getType());
        byteBuf.writeInt(decodeData.getLength());
        byteBuf.writeBytes(decodeData.getData());
    }
}
