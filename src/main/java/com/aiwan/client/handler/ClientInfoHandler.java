package com.aiwan.client.handler;

import com.aiwan.client.service.ClientReceiveMap;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.util.JsonUtil;
import com.aiwan.server.util.ObjectToBytes;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

public class ClientInfoHandler extends SimpleChannelInboundHandler<DecodeData> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DecodeData decodeData) throws Exception {
        //获取方法
        Method method = ClientReceiveMap.getMethod(decodeData.getType());
        //获取bean
        Object bean = ClientReceiveMap.getObject(method);
        //反射调用信息接收方法
        ReflectionUtils.invokeMethod(method,bean,decodeData.getObject());
        //发送提示信息
        System.out.println("请输入1.登录 2.注册 3.注销  4.进入角色系统 5.角色移动 6.地图跳转 7.高级登录 8获取用户信息 9.退出游戏\n10.背包系统" +
                " 11.坐骑系统");
    }
}
