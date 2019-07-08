package com.aiwan.server.base.executor.account;

import com.aiwan.server.base.executor.account.impl.AbstractAccountCommand;
import com.aiwan.server.publicsystem.service.ThreadPoolInit;
import com.aiwan.server.util.GetBean;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author dengzebiao
 * @since 2019.7.2
 * 用户线程池
 */
@Component
public class AccountExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolInit.class);

    /**
     * 用户线程池大小
     */
    private final static int ACCOUNT_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2 * 6 / 10;

    /**
     * 用户线程池
     */
    private final static ThreadPoolExecutor[] ACCOUNT_SERVICE = new ThreadPoolExecutor[ACCOUNT_POOL_SIZE];

    /**
     * 定时线程
     */
    private final static ScheduledExecutorService SCHEDULE_THREAD = Executors.newScheduledThreadPool(1);


    /**
     * 线程池初始化
     */
    public static void initialize() {

        /*
         * 线程池初始化
         * 1.获取用户线程池大小
         * 2.创建线程池
         * */
        logger.debug("创建用户线程数：" + ACCOUNT_POOL_SIZE);
        //用户线程池初始化
        for (int i = 0; i < ACCOUNT_POOL_SIZE; i++) {
            //线程命名
            ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("userThread-pool-" + i).build();
            RejectedExecutionHandler policy = new ThreadPoolExecutor.DiscardPolicy();
            BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(512);
            ACCOUNT_SERVICE[i] = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, queue, nameThreadFactory, policy);
        }

    }

    public void addTask(AbstractAccountCommand accountCommand) {
        int modIndex = accountCommand.modIndex(ACCOUNT_POOL_SIZE);
        ACCOUNT_SERVICE[modIndex].submit(() -> {
            if (!accountCommand.isCanceled()) {
                accountCommand.active();
            }
        });
    }

    /**
     * 延时指令
     */
    public final void schedule(AbstractAccountCommand command, long delay) {
        command.setFuture(GetBean.getScheduleService().schedule(() -> addTask(command), delay));
    }

    public void addTask(String account, Runnable runnable) {
        ACCOUNT_SERVICE[modIndex(account)].execute(runnable);
    }

    private int modIndex(String account) {
        return Math.abs(account.hashCode() % ACCOUNT_POOL_SIZE);
    }
}
