package com.aiwan.server.base.executor;

import com.aiwan.server.base.executor.ICommand;

import java.util.concurrent.ScheduledFuture;

public abstract class AbstractCommand implements ICommand {

    /**
     * 是否被取消
     */
    private boolean isCanceled = false;

    /**
     * 任务名字
     */
    private String taskName;

    /**
     * 定时任务句柄
     */
    private ScheduledFuture future;

    @Override
    public boolean isCanceled() {
        return isCanceled;
    }

    @Override
    public void cancel() {
        if (future != null) {
            future.cancel(true);
        }
        isCanceled = true;
    }

    @Override
    public void refreshState() {
        isCanceled = false;
    }

    public ScheduledFuture getFuture() {
        return future;
    }

    public void setFuture(ScheduledFuture future) {
        this.future = future;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
