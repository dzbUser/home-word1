package com.aiwan.client.socket;

import com.aiwan.client.service.ClientReceiveMap;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author dengzebiao
 * 处理器
 * */
public class ClientInfoHandler extends SimpleChannelInboundHandler<DecodeData> {
    private static Logger logger = LoggerFactory.getLogger(ClientInfoHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DecodeData decodeData) throws Exception {
        //获取方法
        Method method = ClientReceiveMap.getMethod(decodeData.getType());
        //获取bean
        Object bean = ClientReceiveMap.getObject(method);
        //反射调用信息接收方法
        ReflectionUtils.invokeMethod(method,bean,decodeData.getObject());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        logger.debug("断开");
    }
}
