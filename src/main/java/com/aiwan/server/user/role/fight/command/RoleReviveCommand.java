package com.aiwan.server.user.role.fight.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneDelayCommand;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.user.role.fight.pvpUnit.FighterRole;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;

/**
 * 角色复活命令
 *
 * @author dengzebiao
 * @since 2019.7.12
 */
public class RoleReviveCommand extends AbstractSceneDelayCommand {

    private FighterRole fighterRole;

    public RoleReviveCommand(long delay, String accountId, int mapId, FighterRole fighterRole) {
        super(delay, accountId, mapId);
        this.fighterRole = fighterRole;
    }

    @Override
    public void action() {
        MapResource mapResource = GetBean.getMapManager().getMapResource(getMapId());
        //修改角色位置
        Role role = GetBean.getRoleManager().load(fighterRole.getId());
        role.setX(mapResource.getOriginX());
        role.setY(mapResource.getOriginY());
        GetBean.getRoleManager().save(role);
        fighterRole.setPosition(Position.valueOf(mapResource.getOriginX(), mapResource.getOriginY()));
        fighterRole.resetStatus();
        fighterRole.setDeath(false);
    }
}
