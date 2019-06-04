package com.aiwan.server.publicsystem.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author dengzebiao
 * 线程池管理
 * */
public class ThreadPoolManager {
    /** 用户线程池 */
    private static ExecutorService[] userThreadArray;
    /** 其他线程池 */
    private static ExecutorService otherThreadPool;
    /** 用户线程池大小 */
    private static int userPoolSize;
    /** 其他线程大小 */
    private static int otherPoolSize;

    public static void initialize(){

        /*
        * 线程池初始化
        * 1.获取用户线程池大小
        * 2.创建线程池
        * */
        userPoolSize = Runtime.getRuntime().availableProcessors() * 2 * 6 / 10;
        //获取其他线程池
        otherPoolSize = Runtime.getRuntime().availableProcessors() * 2 * 2 / 10;
        otherPoolSize = (otherPoolSize <=0)? 1:otherPoolSize;
        userThreadArray = new ExecutorService[userPoolSize];
        //用户线程池初始化
        for (int i = 0;i<userPoolSize;i++){
            RejectedExecutionHandler policy = new ThreadPoolExecutor.DiscardPolicy();
            BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(512);
            userThreadArray[i] = new ThreadPoolExecutor(1,1,0, TimeUnit.SECONDS,queue,policy);
        }

        //其他线程池初始化
        RejectedExecutionHandler policy = new ThreadPoolExecutor.DiscardPolicy();
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(512);
        otherThreadPool = new ThreadPoolExecutor(otherPoolSize,otherPoolSize,0, TimeUnit.SECONDS,queue,policy);
    }


    /** 根据账号交给某个线程 */
    public static void excuteUserThread(String accountId,Runnable runnable){
        userThreadArray[getNum(accountId)].execute(runnable);
    }

    /** 生成账号获取唯一索引 */
    public static int getNum(String accountId){
        return accountId.length()*15/4*25%userPoolSize;
    }

    /** 执行其他线程池 */
    public static void excuteOtherThread(Runnable runnable){
        otherThreadPool.execute(runnable);
    }
}
