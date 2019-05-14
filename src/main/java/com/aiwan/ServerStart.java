package com.aiwan;

import com.aiwan.netty.NettyServer;
import com.aiwan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerStart {
    private static Logger logger = LoggerFactory.getLogger(ServerStart.class);
    public static void main(String[] args) throws Exception {
        //加载资源
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Hibernate-All-Cfg.xml");
        logger.debug("开始加载资源");
        applicationContext.start();
        NettyServer nettyServer = (NettyServer) applicationContext.getBean("nettyServer");
        //线程启动
        logger.debug("启动Netty服务器");
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            nettyServer.start(applicationContext);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            logger.debug("Netty服务器启动结束");
                        }
                    }
                }
        ).start();
        UserService userService =(UserService) applicationContext.getBean("userService");
        logger.debug("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh:"+userService.getUserByUid(1).getUsername());
    }
}
