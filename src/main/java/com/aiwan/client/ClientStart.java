package com.aiwan.client;

import com.aiwan.client.service.ClientReceiveMap;
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
