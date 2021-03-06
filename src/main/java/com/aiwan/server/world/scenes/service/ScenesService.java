package com.aiwan.server.world.scenes.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.world.base.scene.AbstractScene;

/**
 * @author dengzebiao
 * 场景的移动和跳转
 * */
public interface ScenesService {
    /** 角色移动 */
    void move(Long rId, int x, int y, final Session session);

    /** 角色地图跳转 */
    void shift(Long rId, int map, final Session session);

    /**
     * 更新场景资源
     */
    void updateSceneAttribute(Role role);

    /**
     * 地图改变
     */
    void changeMap(Role role, int targetMapId, int targetSceneId);

    /**
     * 离开滴入
     */
    void leaveMap(Role role);

    /**
     * 进入地图
     *
     * @param role          角色
     * @param abstractScene 场景
     * @param roleUnit      旧战斗单位
     */
    void enterMap(Role role, AbstractScene abstractScene, RoleUnit roleUnit);

    /**
     * 查看地图所有单位
     */
    void viewUnitInMap(int mapId, Session session);

}
