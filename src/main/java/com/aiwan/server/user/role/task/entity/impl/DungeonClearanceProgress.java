package com.aiwan.server.user.role.task.entity.impl;

import com.aiwan.server.user.role.task.entity.AbstractProgressElement;
import com.aiwan.server.user.role.task.process.TaskProgressType;

/**
 * 副本通关进度数据元素
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class DungeonClearanceProgress extends AbstractProgressElement {

    /**
     * 副本所属mapId
     */
    private int mapId;

    public DungeonClearanceProgress(TaskProgressType taskProgressType, int value, int finishValue, int mapId) {
        super(taskProgressType, value, finishValue);
        this.mapId = mapId;
    }

    public DungeonClearanceProgress() {

    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }


}
