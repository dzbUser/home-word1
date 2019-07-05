package com.aiwan.server.publicsystem.service;

import com.aiwan.server.base.executor.account.AccountExecutor;
import com.aiwan.server.base.executor.scene.SceneExecutor;

/**
 * @author dengzebiao
 * 线程池初始化类
 * */
public class ThreadPoolInit {


    /** 线程池初始化 */
    public static void initialize(){
        AccountExecutor.initialize();
        SceneExecutor.initialize();
    }


}
