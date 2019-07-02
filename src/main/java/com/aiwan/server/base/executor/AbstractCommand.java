package com.aiwan.server.base.executor;

import com.aiwan.server.base.executor.ICommand;

import java.util.concurrent.ScheduledFuture;

public abstract class AbstractCommand implements ICommand {

    /**
     * 是否被取消
     */
    private boolean isCanceled = false;

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
}
