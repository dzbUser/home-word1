package com.aiwan.server.base.executor;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
    private final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

    public ScheduledExecutorService getScheduledThreadPool() {
        return scheduledThreadPool;
    }


    /**
     * 定时任务
     */
    public final ScheduledFuture<?> schedule(Runnable task, long delay) {
        return scheduledThreadPool.schedule(task, delay, TimeUnit.MILLISECONDS);
    }


    /**
     * 指定周期执行任务
     *
     * @param task
     * @param delay
     * @param period
     * @return
     */
    public final ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long delay, long period) {
        return scheduledThreadPool.scheduleAtFixedRate(task, delay, period, TimeUnit.MILLISECONDS);
    }
}
