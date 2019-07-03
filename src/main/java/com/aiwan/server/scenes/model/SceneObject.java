package com.aiwan.server.scenes.model;

import com.aiwan.server.user.role.player.model.Role;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * 场景对象
 */
public abstract class SceneObject {

    /**
     * 地图id
     */
    private int mapId = 0;

    /**
     * 场景id
     */
    private int sceneId = 0;

    /**
     * 存储角色
     */
    Map<Long, Role> map = new HashMap<>();

    public int getKey() {
        return mapId | sceneId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    /**
     * 存角色
     */
    public void putRole(Role role) {
        map.put(role.getId(), role);
    }

    /**
     * 移除角色
     */
    public void removeRole(Long rId) {
        map.remove(rId);
    }
}

