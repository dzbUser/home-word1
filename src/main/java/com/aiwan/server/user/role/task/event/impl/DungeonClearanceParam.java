package com.aiwan.server.user.role.task.event.impl;

import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.task.event.AbstractTaskParam;
import com.aiwan.server.user.role.task.process.TaskProgressType;

/**
 * 副本通关参数
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class DungeonClearanceParam extends AbstractTaskParam {

    private int mapId;

    public DungeonClearanceParam(Role role, TaskProgressType taskProgressType, int mapId) {
        super(role, taskProgressType);
        this.mapId = mapId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
