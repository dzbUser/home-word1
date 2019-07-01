package com.aiwan.client.swing.start;

import com.aiwan.client.service.ClientReceiveMap;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.client.swing.clientinterface.HeightLoginInterface;
import com.aiwan.client.util.GetBean;
import com.aiwan.server.util.ResourceUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.*;

/**
 * @author dengzebia
 * @since 2019.6.13
 * 游戏启动
 * */
public class GameStart {

    static Logger logger = LoggerFactory.getLogger(GameStart.class);

    public static void main(String[] args) {
        logger.info("客户端启动开始");
        swingInit();
        resourceInit();
        connect();
        swingInit();
        //启动登录界面
        InterfaceManager.getFrame("login").setVisible(true);
    }

    /** 静态资源初始化 */
    public static void resourceInit(){

        //初始化资源
        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Hibernate-All-Cfg.xml");
        //静态资源初始化
        ResourceUtil.initResource(applicationContext);
        ClientReceiveMap.init(applicationContext);
        GetBean.setApplicationContext(applicationContext);
    }

    /** 网络连接 */
    public static void connect() {
        //连接客户端
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("client-pool-connect").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ClientServerStart.connect();
                } finally {
                    ClientServerStart.close();
                }
            }
        });
    }

    /** 界面初始化 */
    public static void swingInit(){
        new GameInterface();
        new HeightLoginInterface();
    }
}