package com.aiwan.server;

import com.aiwan.server.netty.NettyServer;
import com.aiwan.server.publicsystem.Initialization.*;
import com.aiwan.server.publicsystem.service.ThreadPoolInit;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.ResourceUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.*;

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
        //资源初始化
        ResourceUtil.initResource(applicationContext);
        //初始化反射
        ReflectionInitialization.initialReflection(applicationContext);
        //缓存初始化
        CacheInitialzation.init(applicationContext);
        //线程池初始化
        ThreadPoolInit.initialize();
        //初始化定时线程
        GetBean.getScheduleService().init();
        //初始化地图世界
        GetBean.getMapManager().init();
        logger.debug("启动Netty服务器");
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("server-pool-start-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    nettyServer.start(applicationContext);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    logger.debug("Netty服务器启动结束");
                }
            }
        });
    }
}
