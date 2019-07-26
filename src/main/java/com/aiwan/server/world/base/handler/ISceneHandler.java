package com.aiwan.server.world.base.handler;

import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.user.role.player.model.Role;

/**
 * 场景处理器接口
 *
 * @author dengzebiao
 */
public interface ISceneHandler {


    /**
     * 初始化副本
     */
    void init();

    /**
     * 玩家进入地图
     */
    void enterDungeon(Role role, RoleUnit roleUnit);

    /**
     * 玩家退出地图
     */
    void quitDungeon(Role role);
}
