package com.aiwan.server.base.executor.scene.impl;

public abstract class AbstractSceneRateCommand extends AbstractSceneDelayCommand {

    private long period;

    public AbstractSceneRateCommand(String accountId, int mapId, long delay, long period) {
        super(delay, accountId, mapId);
        this.period = period;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}