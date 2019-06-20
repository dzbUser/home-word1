package com.aiwan.client.swing.start;

import com.aiwan.client.init.ClientResourceInit;
import com.aiwan.client.service.ClientReceiveMap;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.client.swing.clientinterface.HeightLoginInterface;
import com.aiwan.client.util.GetBean;
import com.aiwan.server.publicsystem.Initialization.PropsInitialzation;
import com.aiwan.server.publicsystem.Initialization.RoleResourceInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        connet();
        swingInit();
    }

    /** 静态资源初始化 */
    public static void resourceInit(){

        //初始化资源
        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Hibernate-All-Cfg.xml");
        //道具资源初始化
        PropsInitialzation.init();
        //人物资源初始化
//        RoleResourceInit.init();
        ClientReceiveMap.init(applicationContext);
        //客户端静态资源初始化
        ClientResourceInit.init();
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
        new HeightLoginInterface();
    }
}
