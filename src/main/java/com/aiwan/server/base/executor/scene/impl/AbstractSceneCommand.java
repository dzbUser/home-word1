package com.aiwan.server.base.executor.scene.impl;

import com.aiwan.server.base.executor.AbstractCommand;

public abstract class AbstractSceneCommand extends AbstractCommand {

    /**
     * 判断是否与用户相关
     */
    private String accountId;

    /**
     * 场景id
     */
    private int sceneId;

    /**
     * 地图id,无场景id便用地图id
     */
    private int mapId;

    @Override
    public Integer getKey() {
        return (mapId | sceneId);
    }

    @Override
    public int modIndex(int poolSize) {
        return Math.abs(getKey() % poolSize);
    }

    @Override
    public void active() {
        action();
    }
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public abstract void action();

    public AbstractSceneCommand(String accountId, int mapId) {
        this.accountId = accountId;
        this.sceneId = sceneId;
        this.mapId = mapId;
    }
}
