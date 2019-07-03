package com.aiwan.server.scenes.model;

import com.aiwan.server.user.role.player.model.Role;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * 场景对象
 */
public class SceneObject {

    /**
     * 地图id
     */
    private int mapId;


    /**
     * 存储角色
     */
    Map<Long, Role> roleMap = new HashMap<>();

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }


    /**
     * 存角色
     */
    public void putRole(Role role) {
        roleMap.put(role.getId(), role);
    }

    /**
     * 移除角色
     */
    public void removeRole(Long rId) {
        roleMap.remove(rId);
    }

    /**
     * 获取角色
     */
    public Role getRole(Long rId) {
        return roleMap.get(rId);
    }

    public static SceneObject valueOf(int mapId) {
        SceneObject sceneObject = new SceneObject();
        sceneObject.setMapId(mapId);
        return sceneObject;
    }

    public Map<Long, Role> getRoleMap() {
        return roleMap;
    }

    public void setRoleMap(Map<Long, Role> roleMap) {
        this.roleMap = roleMap;
    }
}

