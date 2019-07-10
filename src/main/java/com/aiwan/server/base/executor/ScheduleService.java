package com.aiwan.server.base.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 定时业务类
 *
 * @author dengzebiao
 * @since 2019.7.8
 */
@Component
public class ScheduleService {

    /**
     * 定时线程
     */
    private ScheduledThreadPoolExecutor scheduledThreadPool;

    private int poolSize = 1;

    /**
     * 定时任务
     */
    public final ScheduledFuture<?> schedule(Runnable task, long delay) {
        return scheduledThreadPool.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 初始化
     */
    public void init() {
        ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("定时器").build();
        scheduledThreadPool = new ScheduledThreadPoolExecutor(poolSize, nameThreadFactory, new ThreadPoolExecutor.DiscardPolicy());
        scheduledThreadPool.prestartAllCoreThreads();
    }

    /**
     * 指定周期执行任务
     *
     * @param task 任务
     * @param delay 延迟
     * @param period 周期
     * @return
     */
    public final ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long delay, long period) {
        return scheduledThreadPool.scheduleAtFixedRate(task, delay, period, TimeUnit.MILLISECONDS);
    }
}