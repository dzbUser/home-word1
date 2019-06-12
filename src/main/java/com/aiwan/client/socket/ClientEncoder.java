package com.aiwan.client.socket;

import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.util.JsonUtil;
import com.aiwan.server.util.ObjectToBytes;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dengzebiao
 * 编码器
 * */
public class ClientEncoder extends MessageToByteEncoder<DecodeData> {

    Logger logger = LoggerFactory.getLogger(ClientEncoder.class);
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, DecodeData decodeData, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(decodeData.getType());
        byte[] data = JsonUtil.object2Bytes(decodeData.getObject());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
    }
}
