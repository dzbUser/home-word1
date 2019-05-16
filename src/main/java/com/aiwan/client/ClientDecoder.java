package com.aiwan.client;
import com.aiwan.publicsystem.DecodeData;
import com.aiwan.util.DeepClone;
import com.aiwan.util.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * 解码器
 * **/
public class ClientDecoder extends ByteToMessageDecoder implements Serializable {
    private DecodeData decodeData;
    Logger logger = LoggerFactory.getLogger(ClientDecoder.class);
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        /*
        * 分四部步
        * 1.读类型
        * 2.读长度
        * 3.读数据
        * 4.转化为DecodeData
        * */
        decodeData = new DecodeData();
        short type = byteBuf.readShort();
        int length = byteBuf.readInt();
//        logger.debug("包的长度："+byteBuf.readableBytes()+" length:"+length);
//        logger.debug("length:"+length+"all:");
        if (byteBuf.readableBytes()< length){
            byteBuf.resetReaderIndex();
        }else {
            byte[] data = new byte[length];
            byteBuf.readBytes(data);
            decodeData.setType(type);
            decodeData.setLength(length);
            decodeData.setData(data);
            list.add(decodeData);
        }

    }
}
