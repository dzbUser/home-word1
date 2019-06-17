package com.aiwan.client;

import com.aiwan.client.init.ClientResourceInit;
import com.aiwan.client.service.ClientReceiveMap;
import com.aiwan.server.publicsystem.Initialization.PropUseInitialization;
import com.aiwan.server.publicsystem.Initialization.PropsInitialzation;
import com.aiwan.server.publicsystem.Initialization.RoleResourceInit;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 客户端启动
 * @author dengzebiao
 * @since 2019.6.11
 * */
public class ClientStart {

    private static Logger logger = LoggerFactory.getLogger(ClientStart.class);

    public static void main(String[] args) throws Exception {

        logger.debug("加载资源");
        //初始化资源
        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Hibernate-All-Cfg.xml");
        logger.debug("开始加载静态资源");
        //道具资源初始化
        PropsInitialzation.init();
        PropUseInitialization.initialization(applicationContext);
        //人物资源初始化
        RoleResourceInit.init();
        //客户端静态资源初始化
        ClientResourceInit.init();

        ClientReceiveMap.init(applicationContext);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Channel channel = ClientServer.connect();
                        }finally {
                        }
                    }
                }
        ).start();
    }
}
