package com.aiwan.server.base.event.event.impl;

import com.aiwan.server.base.event.event.IEvent;
import com.aiwan.server.user.role.player.model.Role;

/**
 * 副本通关事件
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class DungeonClearanceEvent implements IEvent {

    private Role role;

    /**
     * 通关副本类型
     */
    private int mapId;

    public static DungeonClearanceEvent valueOf(Role role, int mapId) {
        DungeonClearanceEvent dungeonClearanceEvent = new DungeonClearanceEvent();
        dungeonClearanceEvent.setRole(role);
        dungeonClearanceEvent.setMapId(mapId);
        return dungeonClearanceEvent;
    }

    @Override
    public long getOwner() {
        return role.getId();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
