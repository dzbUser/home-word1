package com.aiwan.client;

import com.aiwan.client.service.ClientReceiveMap;
import com.aiwan.server.publicsystem.Initialization.PropUseInitialization;
import com.aiwan.server.publicsystem.Initialization.PropsInitialzation;
import com.aiwan.server.publicsystem.Initialization.RoleResourceInit;
import io.netty.channel.Channel;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 客户端启动
 * @author dengzebiao
 * @since 2019.6.11
 * */
public class ClientStart {
    public static void main(String[] args) throws Exception {
        //初始化资源
        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Hibernate-All-Cfg.xml");
        //道具资源初始化
        PropsInitialzation.init();
        PropUseInitialization.initialization(applicationContext);
        //人物资源初始化
        RoleResourceInit.init();

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
