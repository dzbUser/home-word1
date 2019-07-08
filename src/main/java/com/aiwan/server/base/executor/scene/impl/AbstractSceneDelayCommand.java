package com.aiwan.server.base.executor.scene.impl;

/**
 * 抽象定时场景命令
 *
 * @author dengzebiao
 * @since 2019.7.9
 */
public abstract class AbstractSceneDelayCommand extends AbstractSceneCommand {

    /**
     * 延时
     */
    private long delay;

    public AbstractSceneDelayCommand(long delay, String accountId, int mapId) {
        super(accountId, mapId);
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }


}
