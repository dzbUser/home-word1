package com.aiwan.client;

import com.aiwan.publicsystem.CM_Map;
import com.aiwan.publicsystem.DecodeData;
import com.aiwan.role.protocol.CM_UserMessage;
import com.aiwan.scenes.protocol.CM_Move;
import com.aiwan.scenes.protocol.CM_Shift;
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
            System.out.println("请输入1.登录 2.注册 3.注销 4.角色移动 5.地图跳转");
            while(true){
                CM_UserMessage userMessage = new CM_UserMessage();
                DecodeData decodeData = new DecodeData();
                byte[] data = "初始化".getBytes();
                int num = scanner.nextInt();
                if(num == 1){
                    if (!LoginUser.getUsername().equals("")){
                        System.out.println("您已经登录过了！");
                        continue;
                    }
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
                    if (!LoginUser.getUsername().equals("")){
                        System.out.println("您已经登录，请退出后再注册！");
                        continue;
                    }
                    System.out.println("注册用户开始\n请输入注册账号");
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
                    if (LoginUser.getUsername().equals("")){
                        System.out.println("您还未登录，请登录游戏！");
                        continue;
                    }
                    userMessage.setUsername(LoginUser.getUsername());
                    userMessage.setPassword("");
                    data = DeepClone.writeInto(userMessage);
                    decodeData.setType(Protocol.LOGOUT);
                    decodeData.setLength(data.length);
                    decodeData.setData(data);
                    channel.writeAndFlush(decodeData);
                }
                else  if (num == 4){
                    if (LoginUser.getUsername().equals("")){
                        System.out.println("您还未登录，请登录游戏！");
                        continue;
                    }
                    System.out.println(LoginUser.getMapMessage());
                    scanner.nextLine();
                    System.out.println("请输入您要移动的横坐标");
                    short x = scanner.nextShort();
                    System.out.println("请输入您要移动的横坐标纵坐标");
                    short y = scanner.nextShort();
                    CM_Move move = new CM_Move(LoginUser.getCurrentX(),LoginUser.getCurrentY(),x,y,LoginUser.getUsername());
                    decodeData.setType(Protocol.MOVE);
                    data = DeepClone.writeInto(move);
                    decodeData.setLength(data.length);
                    decodeData.setData(data);
                    channel.writeAndFlush(decodeData);
                }else if (num == 5){
                    if (LoginUser.getUsername().equals("")){
                        System.out.println("您还未登录，请登录游戏！");
                        continue;
                    }
                    System.out.println(LoginUser.getMapMessage());
                    System.out.println("请选择您要跳转的地图；1主城 2野外");
                    scanner.nextLine();
                    short map = scanner.nextShort();
                    if (map > 2){
                        System.out.println("没有这个地图，请重新选择");
                        continue;
                    }else {
                        CM_Shift cm_shift = new CM_Shift(LoginUser.getUsername(),map);
                        decodeData.setType(Protocol.SHIFT);
                        data = DeepClone.writeInto(cm_shift);
                        decodeData.setLength(data.length);
                        decodeData.setData(data);
                        channel.writeAndFlush(decodeData);
                    }
                }else {
                    System.out.println("尊敬的用户，您的输入不规格，请重新输入");
                    System.out.println("请输入1.登录 2.注册 3.注销 4.角色移动 5.地图跳转");
                    continue;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
        }
        return null;
    }

}