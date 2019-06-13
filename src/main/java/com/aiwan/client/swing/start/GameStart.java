package com.aiwan.client.swing.start;

import com.aiwan.client.service.ClientReceiveMap;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.server.publicsystem.Initialization.PropUseInitialization;
import com.aiwan.server.publicsystem.Initialization.PropsInitialzation;
import com.aiwan.server.publicsystem.Initialization.RoleResourceInit;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;

/**
 * @author dengzebia
 * @since 2019.6.13
 * 游戏启动
 * */
public class GameStart {


    public static void main(String[] args) {
        resourceInit();
        connet();
    }

    /** 静态资源初始化 */
    public static void resourceInit(){

        //初始化资源
        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Hibernate-All-Cfg.xml");
        //道具资源初始化
        PropsInitialzation.init();
        PropUseInitialization.initialization(applicationContext);
        //人物资源初始化
        RoleResourceInit.init();
        ClientReceiveMap.init(applicationContext);
    }

    /** 网络连接 */
    public static void connet(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ClientServerStart.connect();
                        }finally {
                        }
                    }
                }
        ).start();
    }
}
