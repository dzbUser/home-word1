package com.aiwan.server.base.executor.scene;

import com.aiwan.server.base.executor.AbstractCommand;
import com.aiwan.server.base.executor.ICommand;
import com.aiwan.server.util.GetBean;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 场景线程池
 *
 * @author dengzebiao
 */
@Component
public class SceneExecutor {

    private Logger logger = LoggerFactory.getLogger(SceneExecutor.class);

    private static final int SCENE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2 * 2 / 10;

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
        final String taskName = command.getTaskName();
        SCENE_SERVICE[modIndex].submit(() -> {
            try {
                if (!command.isCanceled()) {
                    command.active();
                }
            } catch (Exception e) {
                //之前未加异常捕获，出错时不捕获异常
                logger.error("SceneExecutor执行任务{}错误:{}", taskName, e.getLocalizedMessage());
            }

        });
    }


    private int modIndex(String account) {
        return Math.abs(account.hashCode() % SCENE_POOL_SIZE);
    }

    /**
     * 定时命令
     *
     * @param command 命令实体
     * @param delay 延迟
     */
    public final void schedule(AbstractCommand command, long delay) {

        command.setFuture(GetBean.getScheduleService().schedule(() -> addTask(command), delay));

    }

    /**
     * 周期命令
     *
     * @param command 命令实体
     * @param delay 延迟
     * @param period 周期
     */
    public final void schedule(AbstractCommand command, long delay, long period) {

        command.setFuture(GetBean.getScheduleService().scheduleAtFixedRate(() -> addTask(command), delay, period));
    }
}
