package com.aiwan.client;

import com.aiwan.client.clientservice.RoleService;
import com.aiwan.server.netty.Decoder;
import com.aiwan.server.netty.Encoder;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.user.account.protocol.*;
import com.aiwan.server.scenes.protocol.CM_Move;
import com.aiwan.server.scenes.protocol.CM_Shift;
import com.aiwan.server.util.ObjectToBytes;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
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
                    ch.pipeline().addLast(new Decoder());
                    ch.pipeline().addLast(new Encoder());
                    ch.pipeline().addLast(new ClientHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            Channel channel = channelFuture.channel();
            //发送心跳
            sendHear(channel);
            //获取客户端屏幕的写入
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入1.登录 2.注册 3.注销  4.进入角色系统 5.角色移动 6.地图跳转 7.高级登录 8获取用户信息 9.退出游戏");
            while(true){
                DecodeData decodeData = new DecodeData();
                byte[] data = "初始化".getBytes();
                int num = scanner.nextInt();
                if(num == 1){
                    if (!LoginUser.getUsername().equals("")){
                        System.out.println("您已经登录过了！");
                        continue;
                    }
                    login(channel,scanner,decodeData);
                }else if (num == 2){
                    if (!LoginUser.getUsername().equals("")){
                        System.out.println("您已经登录，请退出后再注册！");
                        continue;
                    }
                    registered(channel,scanner,decodeData);

                }else if (num == 3){
                    if (LoginUser.getUsername().equals("")){
                        System.out.println("您还未登录，请登录游戏！");
                        continue;
                    }
                    logout(channel,scanner,decodeData);
                }else if (num == 4){
                    if (LoginUser.getUsername().equals("")){
                        System.out.println("您还未登录，请登录游戏！");
                        continue;
                    }
                    RoleService.roleSystem(channel);
                }
                else  if (num == 5){
                    if (LoginUser.getUsername().equals("")||LoginUser.getRoles() ==null || LoginUser.getRoles().size() == 0){
                        System.out.println("您还未登录，或者还未创建角色，请登录游戏！");
                        continue;
                    }
                    System.out.println(LoginUser.getMapMessage());
                    scanner.nextLine();
                    System.out.println("请输入您要移动的横坐标");
                    Integer x = scanner.nextInt();
                    System.out.println("请输入您要移动的横坐标纵坐标");
                    Integer y =  scanner.nextInt();
                    CM_Move move = new CM_Move();
                    move.setCurrentX(LoginUser.getCurrentX());
                    move.setCurrentY(LoginUser.getCurrentY());
                    move.setTargetX(x);
                    move.setTargetY(y);
                    move.setUsername(LoginUser.getUsername());
                    decodeData.setType(Protocol.MOVE);
                    data = ObjectToBytes.writeInto(move);
                    decodeData.setLength(data.length);
                    decodeData.setData(data);
                    channel.writeAndFlush(decodeData);
                }else if (num == 6){
                    if (LoginUser.getUsername().equals("")||LoginUser.getRoles() ==null || LoginUser.getRoles().size() == 0){
                        System.out.println("您还未登录，或者还未创建角色，请登录游戏！");
                        continue;
                    }
                    System.out.println(LoginUser.getMapMessage());
                    System.out.println("请选择您要跳转的地图；1主城 2野外");
                    scanner.nextLine();
                    int map = scanner.nextInt();
                    if (map > 2){
                        System.out.println("没有这个地图，请重新选择");
                        continue;
                    }else {
                        CM_Shift cm_shift = new CM_Shift();
                        cm_shift.setMap(map);
                        cm_shift.setUsername(LoginUser.getUsername());
                        decodeData.setType(Protocol.SHIFT);
                        data = ObjectToBytes.writeInto(cm_shift);
                        decodeData.setLength(data.length);
                        decodeData.setData(data);
                        channel.writeAndFlush(decodeData);
                    }
                }else if (num == 7){
                    if (!LoginUser.getUsername().equals("")){
                        System.out.println("您已经登录过了！");
                        continue;
                    }
                    hlogin(channel,scanner,decodeData);
                }
                else if (num == 8){
                    if (LoginUser.getUsername().equals("")){
                        System.out.println("您还未登录过了！");
                        continue;
                    }
                    getUserMessage(channel,LoginUser.getUsername());

                }
                else if (num == 9){
                    if (!LoginUser.getUsername().equals("")){
                        logout(channel,scanner,decodeData);
                    }
                    channel.close();
                    System.exit(0);
                }
                else {
                    System.out.println("尊敬的用户，您的输入不规格，请重新输入");
                    System.out.println("请输入1.登录 2.注册 3.注销  4.进入角色系统 5.角色移动 6.地图跳转 7.高级登录 8获取用户信息 9.退出游戏");
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

    //登录函数
    private static void login(Channel channel, Scanner scanner, DecodeData decodeData){
        scanner.nextLine();
        System.out.println("登录开始，请输入账号");
        CM_Login cm_login = new CM_Login();
        String username = scanner.nextLine();
        System.out.println("请输入密码");
        String password = scanner.nextLine();
        cm_login.setUsername(username);
        cm_login.setPassword(password);
        byte[] data  = ObjectToBytes.writeInto(cm_login);
        decodeData.setType(Protocol.LOGIN);
        decodeData.setLength(data.length);
        decodeData.setData(data);
        channel.writeAndFlush(decodeData);
    }

    //注册函数
    private static void hlogin(Channel channel, Scanner scanner, DecodeData decodeData){
        scanner.nextLine();
        System.out.println("高级登录开始，请输入账号");
        CM_HLogin cm_login = new CM_HLogin();
        String username = scanner.nextLine();
        System.out.println("请高级输入密码");
        String hpassword = scanner.nextLine();
        cm_login.setUsername(username);
        cm_login.setHpassword(hpassword);
        byte[]data  = ObjectToBytes.writeInto(cm_login);
        decodeData.setType(Protocol.HLOGIN);
        decodeData.setLength(data.length);
        decodeData.setData(data);
        channel.writeAndFlush(decodeData);
    }
    //注销函数
    private static void logout(Channel channel, Scanner scanner, DecodeData decodeData){
        CM_Logout cm_logout = new CM_Logout();
        cm_logout.setUsername(LoginUser.getUsername());
        byte[] data = ObjectToBytes.writeInto(cm_logout);
        decodeData.setType(Protocol.LOGOUT);
        decodeData.setLength(data.length);
        decodeData.setData(data);
        channel.writeAndFlush(decodeData);
    }
    private static void registered(Channel channel,Scanner scanner,DecodeData decodeData){
        System.out.println("注册用户开始\n请输入注册账号");
        scanner.nextLine();
        String username = scanner.nextLine();
        System.out.println("请输入密码");
        String password = scanner.nextLine();
        System.out.println("请输入高级密码");
        String hpassword = scanner.nextLine();
        CM_Registered cm_registered = new CM_Registered();
        cm_registered.setUsername(username);
        cm_registered.setPassword(password);
        cm_registered.setHpassword(hpassword);
        byte[] data = ObjectToBytes.writeInto(cm_registered);
        decodeData.setType(Protocol.REGIST);
        decodeData.setLength(data.length);
        decodeData.setData(data);
        channel.writeAndFlush(decodeData);
    }

    //发送心跳
    public static void sendHear(final Channel channel){
        final DecodeData decodeData = new DecodeData();
        final byte[] data = ObjectToBytes.writeInto("heart");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    decodeData.setType(Protocol.HEART);
                    decodeData.setLength(data.length);
                    decodeData.setData(data);
                    channel.writeAndFlush(decodeData);
                }
            }
        }).start();
    }

    //获取个人信息
    public static void getUserMessage(final Channel channel,String username){
        CM_UserMessage cm_userMessage = new CM_UserMessage();
        cm_userMessage.setUsername(username);
        DecodeData decodeData = SMToDecodeData.shift(Protocol.USERMESSAGE,cm_userMessage);
        channel.writeAndFlush(decodeData);
    }
}