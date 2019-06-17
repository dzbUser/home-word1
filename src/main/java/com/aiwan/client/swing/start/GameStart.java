package com.aiwan.client.swing.start;

import com.aiwan.client.service.ClientReceiveMap;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.NavigationBar;
import com.aiwan.client.swing.clientInterface.GameInterface;
import com.aiwan.client.util.GetBean;
import com.aiwan.server.publicsystem.Initialization.PropUseInitialization;
import com.aiwan.server.publicsystem.Initialization.PropsInitialzation;
import com.aiwan.server.publicsystem.Initialization.RoleResourceInit;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author dengzebia
 * @since 2019.6.13
 * 游戏启动
 * */
public class GameStart {


    public static void main(String[] args) {
        swingInit();
        resourceInit();
        connet();
        swingInit();
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
        GetBean.setApplicationContext(applicationContext);
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

    /** 界面初始化 */
    public static void swingInit(){
        new GameInterface();
    }
}
