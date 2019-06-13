package com.aiwan.client.socket;

import com.aiwan.client.ClientServer;
import com.aiwan.client.handler.ClientInfoHandler;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.util.Protocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dengzebia
 * @since 2019.6.13
 * 客户端网络连接
 * */
public class ClientServerStart {

    private static Logger logger = LoggerFactory.getLogger(ClientServerStart.class);
    private static int port = 8001;
    private static String ip = "localhost";
    /** 信息发送*/
    private static Channel channel;

    public static Channel connect() {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new ClientDecoder());
                    ch.pipeline().addLast(new ClientEncoder());
                    ch.pipeline().addLast(new ClientInfoHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            ClientServerStart.channel = channelFuture.channel();
            //发送心跳
            sendHear(channel);
            while (true){}
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
            channel.close();
        }
        return null;
    }

    //发送心跳
    public static void sendHear(final Channel channel){
        final DecodeData decodeData = new DecodeData();
        decodeData.setType(Protocol.HEART);
        decodeData.setObject("hear");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    channel.writeAndFlush(decodeData);
                }
            }
        }).start();
    }

    public static Channel getChannel() {
        return ClientServerStart.channel;
    }

    public static void sendMessage(Object object){
        ClientServerStart.channel.writeAndFlush(object);
    }

    public static void close(){
        ClientServerStart.channel.close();
    }
}
