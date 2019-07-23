package com.aiwan.server.base.executor.scene.impl;

/**
 * 场景循环抽象类
 */
public abstract class AbstractSceneRateCommand extends AbstractSceneDelayCommand {

    /**
     * 循环周期
     */
    private long period;

    public AbstractSceneRateCommand(String accountId, int mapId, long delay, long period) {
        super(delay, accountId, mapId);
        this.period = period;
    }

    public AbstractSceneRateCommand(String accountId, int mapId, int sceneId, long delay, long period) {
        super(delay, accountId, mapId, sceneId);
        this.period = period;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}