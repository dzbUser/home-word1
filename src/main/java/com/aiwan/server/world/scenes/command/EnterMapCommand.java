package com.aiwan.server.world.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.world.scenes.mapresource.MapResource;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;

/**
 * 进入地图命令(需要初始化坐标)
 *
 * @author dengzebiao
 * @since 2019.7.4
 */
public class EnterMapCommand extends AbstractSceneCommand {

    /**
     * 角色
     */
    private Role role;

    /**
     * 原先战斗单元
     */
    private RoleUnit roleUnit;

    /**
     * 目标地图id
     */
    private int targetSceneId;

    @Override
    public void action() {
        //获取地图资源
        MapResource mapResource;
        mapResource = GetBean.getMapManager().getMapResource(getMapId());
        //初始化化角色坐标
        role.setMap(getKey());
        role.setX(mapResource.getOriginX());
        role.setY(mapResource.getOriginY());
        //添加到地图资源中
        RoleUnit newFightRole = RoleUnit.valueOf(role, mapResource.getMapId());
        //传递cd、hp以及mp
        if (roleUnit != null) {
            newFightRole.transferStatus(roleUnit);
        }
        GetBean.getMapManager().putFighterRole(newFightRole);
        //写回
        GetBean.getRoleManager().save(role);
        //设置跳转结束
        role.setChangingMap(false);
        //给所有玩家发送消息
        GetBean.getMapManager().sendMessageToUsers(getKey());
    }

    public EnterMapCommand(int mapId, Role role, RoleUnit roleUnit) {
        super(role.getAccountId(), mapId);
        setTaskName("进入地图命令");
        this.targetSceneId = 0;
        this.role = role;
        this.roleUnit = roleUnit;
    }

    public EnterMapCommand(int mapId, int sceneId, Role role, RoleUnit roleUnit) {
        super(role.getAccountId(), mapId, sceneId);
        setTaskName("进入地图命令");
        this.role = role;
        this.roleUnit = roleUnit;
        this.targetSceneId = mapId;
    }

}
