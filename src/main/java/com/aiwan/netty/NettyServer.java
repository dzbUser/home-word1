package com.aiwan.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * netty启动类
 * */
@Component("nettyServer")
public class NettyServer {
    private final int port = 8001;

    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);
    public void start(ClassPathXmlApplicationContext classPathXmlApplicationContext) throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        b.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                            throws Exception {
                        logger.debug("initChannel ch:" + ch);
                        ch.pipeline()
                                .addLast("decoder", new Decoder())   // 1
                                .addLast("encoder", new Encoder())  // 2
//                                .addLast("aggregator", new HttpObjectAggregator(256 * 1024))    // 3
                                .addLast("handler", new Handler());        // 4
//                        ch.pipeline().addLast(new ObjectEncoder());
//                        ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE,
//                                ClassResolvers.cacheDisabled(null)));
//                        ch.pipeline().addLast((ChannelHandler) classPathXmlApplicationContext.getBean("objectServerHandler"));
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) // determining the number of connections queued
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

        b.bind(port).sync();
    }

}
