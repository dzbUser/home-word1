package com.aiwan.server.user.role.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.scenes.fight.model.pvpunit.BaseUnit;
import com.aiwan.server.scenes.fight.model.pvpunit.FighterRole;
import com.aiwan.server.scenes.model.SceneObject;
import com.aiwan.server.scenes.service.MapManager;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;

/**
 * 重置场合内战斗单元状态命令
 *
 * @author dengzebiao
 * @since 2019.7.10
 */
public class ResetStatusCommand extends AbstractSceneCommand {
    private Role role;

    public ResetStatusCommand(String accountId, int mapId, Role role) {
        super(accountId, mapId);
        this.role = role;
    }

    @Override
    public void action() {
        SceneObject sceneObject = GetBean.getMapManager().getSceneObject(getKey());
        if (sceneObject != null) {
            //场景不为空
            FighterRole fighterRole = (FighterRole) sceneObject.getBaseUnit(role.getId());
            if (fighterRole != null) {
                fighterRole.resetStatus(role);
            }

        }
    }
}
