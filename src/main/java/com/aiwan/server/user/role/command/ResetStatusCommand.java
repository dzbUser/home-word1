package com.aiwan.server.user.role.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.world.base.scene.AbstractScene;
import com.aiwan.server.world.base.scene.UniqueScene;
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

    public ResetStatusCommand(String accountId, Role role) {
        super(accountId, role.getMap(), role.getSceneId());
        this.role = role;
        setTaskName("重置场合内战斗单元状态命令");
    }

    @Override
    public void action() {
        AbstractScene abstractScene = GetBean.getMapManager().getSceneObject(getMapId(), getSceneId());
        if (abstractScene != null) {
            //场景不为空
            RoleUnit roleUnit = (RoleUnit) abstractScene.getBaseUnit(role.getId());
            if (roleUnit != null) {
                roleUnit.resetStatus(role);
            }

        }
    }

    @Override
    public String getTaskName() {
        return "ResetStatusCommand";
    }
}
