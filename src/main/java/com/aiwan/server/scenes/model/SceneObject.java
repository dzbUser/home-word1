package com.aiwan.server.scenes.model;

import com.aiwan.server.scenes.fight.model.pvpunit.FighterRole;
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
    Map<Long, FighterRole> fighterRoleMap = new HashMap<>();

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }


    /**
     * 存角色
     */
    public void putFighterRole(Role role) {
        fighterRoleMap.put(role.getId(), FighterRole.valueOf(role));
    }

    /**
     * 移除角色
     */
    public void removeFighterRole(Long rId) {
        fighterRoleMap.remove(rId);
    }

    /**
     * 获取角色
     */
    public FighterRole getFighterRole(Long rId) {
        return fighterRoleMap.get(rId);
    }

    /**
     * 创建场景
     */
    public static SceneObject valueOf(int mapId) {
        SceneObject sceneObject = new SceneObject();
        sceneObject.setMapId(mapId);
        return sceneObject;
    }

    public Map<Long, FighterRole> getFighterRoleMap() {
        return fighterRoleMap;
    }

    public void setFighterRoleMap(Map<Long, FighterRole> fighterRoleMap) {
        this.fighterRoleMap = fighterRoleMap;
    }
}

