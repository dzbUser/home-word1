package com.aiwan.server.publicsystem.service;

import com.aiwan.server.publicsystem.Initialization.MapInitialization;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author dengzebiao
 * 线程池管理
 * */
public class ThreadPoolManager {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolManager.class);

    /** 用户线程池 */
    private static ExecutorService[] userThreadArray;

    /** 用户线程池大小 */
    private static int userPoolSize;


    /** 线程池初始化 */
    public static void initialize(){

        /*
        * 线程池初始化
        * 1.获取用户线程池大小
        * 2.创建线程池
        * */
        userPoolSize = Runtime.getRuntime().availableProcessors() * 2 * 6 / 10;
        userThreadArray = new ExecutorService[userPoolSize];
        logger.debug("创建用户线程数："+userPoolSize);
        //用户线程池初始化
        for (int i = 0;i<userPoolSize;i++){
            //线程命名
            ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("userThread-pool-"+i).build();
            RejectedExecutionHandler policy = new ThreadPoolExecutor.DiscardPolicy();
            BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(512);
            userThreadArray[i] = new ThreadPoolExecutor(1,1,0, TimeUnit.SECONDS,queue,nameThreadFactory,policy);
        }

    }


    /** 根据账号交给某个线程 */
    public static void executeUserThread(String accountId, Runnable runnable){
        userThreadArray[getNum(accountId)].execute(runnable);
    }

    /** 生成账号获取唯一索引 */
    public static int getNum(String accountId){
        return accountId.hashCode()%userPoolSize;
    }

}
