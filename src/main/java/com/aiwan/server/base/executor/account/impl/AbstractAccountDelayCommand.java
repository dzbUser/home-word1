package com.aiwan.server.base.executor.account.impl;

public abstract class AbstractAccountDelayCommand extends AbstractAccountCommand {

    /**
     * 延时
     */
    private long delay;

    public AbstractAccountDelayCommand(long delay, String accountId) {
        super(accountId);
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
