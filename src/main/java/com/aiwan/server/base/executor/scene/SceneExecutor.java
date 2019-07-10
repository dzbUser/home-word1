package com.aiwan.server.base.executor.scene;

import com.aiwan.server.base.executor.AbstractCommand;
import com.aiwan.server.base.executor.ICommand;
import com.aiwan.server.util.GetBean;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 场景线程池
 *
 * @author dengzebiao
 */
@Component
public class SceneExecutor {

    private static final int SCENE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 4 / 10;

    private static final ThreadPoolExecutor[] SCENE_SERVICE = new ThreadPoolExecutor[SCENE_POOL_SIZE];

    /**
     * 线程池初始化
     */
    public static void initialize() {
        for (int i = 0; i < SCENE_POOL_SIZE; i++) {
            //线程命名
            ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("sceneThread-pool-" + i).build();
            RejectedExecutionHandler policy = new ThreadPoolExecutor.DiscardPolicy();
            SCENE_SERVICE[i] = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), nameThreadFactory, policy);
        }

    }

    public void addTask(ICommand command) {
        int modIndex = command.modIndex(SCENE_POOL_SIZE);
        SCENE_SERVICE[modIndex].submit(() -> {
            if (!command.isCanceled()) {
                command.active();
            }
        });
    }


    private int modIndex(String account) {
        return Math.abs(account.hashCode() % SCENE_POOL_SIZE);
    }

    /**
     * 定时命令
     *
     * @param command
     * @param delay
     */
    public final void schedule(AbstractCommand command, long delay) {

        command.setFuture(GetBean.getScheduleService().schedule(() -> addTask(command), delay));

    }

    /**
     * 周期命令
     *
     * @param command
     * @param delay
     * @param period
     */
    public final void schedule(AbstractCommand command, long delay, long period) {

        command.setFuture(GetBean.getScheduleService().scheduleAtFixedRate(() -> addTask(command), delay, period));
    }
}
