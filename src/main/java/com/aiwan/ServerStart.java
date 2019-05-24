package com.aiwan;

import com.aiwan.netty.NettyServer;
import com.aiwan.publicsystem.Initialization.CacheInitialzation;
import com.aiwan.publicsystem.Initialization.MapInitialization;
import com.aiwan.publicsystem.Initialization.ReflectionInitialization;
import com.aiwan.publicsystem.service.TheatpoolManager;
import com.aiwan.user.entity.UserEnt;
import com.aiwan.user.service.UserManager;
import com.aiwan.util.GetBean;
import com.aiwan.util.RedisOperating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 服务器启动类
 * */
public class ServerStart {
    private static Logger logger = LoggerFactory.getLogger(ServerStart.class);
    public static void main(String[] args) throws Exception {

        //加载资源
        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Hibernate-All-Cfg.xml");
        logger.debug("开始加载资源");
        applicationContext.start();
        final NettyServer nettyServer = (NettyServer) applicationContext.getBean("nettyServer");
        //地图资源初始化
        MapInitialization.init();
        //初始化反射
        ReflectionInitialization.initialReflection(applicationContext);
        //redis初始化
        RedisOperating.init("localhost");
        //缓存初始化
        CacheInitialzation.init(applicationContext);
        //线程池初始化
        TheatpoolManager.initialize();
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
    }
}
