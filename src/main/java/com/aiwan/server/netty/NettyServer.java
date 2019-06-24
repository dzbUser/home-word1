package com.aiwan.server.netty;

import com.aiwan.server.publicsystem.handler.HeardExaminationHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author dengzebiao
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
                                .addLast("decoder", new Decoder())
                                .addLast("encoder", new Encoder())
                                .addLast(new IdleStateHandler(30,0,0, TimeUnit.SECONDS))
                                .addLast(new HeardExaminationHandler())
                                .addLast("handler", new Handler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

        b.bind(port).sync();
    }

}
