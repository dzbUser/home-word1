package com.aiwan.client;

import com.aiwan.publicsystem.DecodeData;
import com.aiwan.role.protocol.CM_UserMessage;
import com.aiwan.util.DeepClone;
import com.aiwan.util.Protocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ClientServer {
    private static Logger logger = LoggerFactory.getLogger(ClientServer.class);
    private static int port = 8001;
    private static String ip = "localhost";
    public static Channel connect(){
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new ClientDecoder());
                    ch.pipeline().addLast(new ClientEncoder());
                    ch.pipeline().addLast(new ClientHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            Channel channel = channelFuture.channel();
            //获取客户端屏幕的写入
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入1.登录 2.注册 3.注销");
            while(true){
                CM_UserMessage userMessage = new CM_UserMessage();
                DecodeData decodeData = new DecodeData();
                byte[] data = "初始化".getBytes();
                int num = scanner.nextInt();
                if(num == 1){
                    scanner.nextLine();
                    System.out.println("登录开始，请输入账号");
                    String username = scanner.nextLine();
                    System.out.println("请输入密码");
                    String password = scanner.nextLine();
                    userMessage.setUsername(username);
                    userMessage.setPassword(password);
                    data  = DeepClone.writeInto(userMessage);
                    decodeData.setType(Protocol.LOGIN);
                    decodeData.setLength(data.length);
                    decodeData.setData(data);
                    channel.writeAndFlush(decodeData);
                }else if (num == 2){
                    System.out.println("注册用户开发，请输入注册账号");
                    scanner.nextLine();
                    String username = scanner.nextLine();
                    System.out.println("请输入密码");
                    String password = scanner.nextLine();
                    userMessage.setUsername(username);
                    userMessage.setPassword(password);
                    data = DeepClone.writeInto(userMessage);
                    decodeData.setType(Protocol.REGIST);
                    decodeData.setLength(data.length);
                    decodeData.setData(data);
                    channel.writeAndFlush(decodeData);
                }else if (num == 3){
                    if (LoginUser.username.equals("")){
                        System.out.println("您还未登录，请登录游戏！");
                        continue;
                    }
                    userMessage.setUsername(LoginUser.username);
                    userMessage.setPassword("");
                    data = DeepClone.writeInto(userMessage);
                    decodeData.setType(Protocol.LOGOUT);
                    decodeData.setLength(data.length);
                    decodeData.setData(data);
                    channel.writeAndFlush(decodeData);
                }
                logger.debug(num+"");
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
        }
        return null;
    }

}