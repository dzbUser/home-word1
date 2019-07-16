package com.aiwan.server.user.role.fight.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneDelayCommand;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.user.role.fight.pvpUnit.RoleUnit;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;

/**
 * 角色复活命令
 *
 * @author dengzebiao
 * @since 2019.7.12
 */
public class RoleReviveCommand extends AbstractSceneDelayCommand {

    private RoleUnit roleUnit;

    public RoleReviveCommand(long delay, String accountId, int mapId, RoleUnit roleUnit) {
        super(delay, accountId, mapId);
        this.roleUnit = roleUnit;
        setTaskName("角色复活命令");
    }

    @Override
    public void action() {
        MapResource mapResource = GetBean.getMapManager().getMapResource(getMapId());
        //修改角色位置
        Role role = GetBean.getRoleManager().load(roleUnit.getId());
        role.setX(mapResource.getOriginX());
        role.setY(mapResource.getOriginY());
        GetBean.getRoleManager().save(role);
        roleUnit.setPosition(Position.valueOf(mapResource.getOriginX(), mapResource.getOriginY()));
        roleUnit.resetStatus();
        roleUnit.setDeath(false);
        //发送地图到本地图所有玩家
        GetBean.getMapManager().sendMessageToUsers(getMapId());
    }
}
