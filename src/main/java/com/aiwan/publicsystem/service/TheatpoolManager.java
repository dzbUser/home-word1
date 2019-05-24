package com.aiwan.publicsystem.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 线程池管理
 * */
public class TheatpoolManager {
    private static Map<String, Executor> executorMap = new HashMap<>();

    public static void initialize(){
        //用户运行线程池
        executorMap.put("user",Executors.newFixedThreadPool(10));
        //场景运行线程池
        executorMap.put("scenes",Executors.newFixedThreadPool(10));
    }

    public static void start(String name,Runnable runnable){
        Executor executor = executorMap.get(name);
        if (executor!=null){
            executor.execute(runnable);
        }
    }
}
