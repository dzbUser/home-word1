package com.aiwan.server.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;

/**
 * 更新场景中的角色属性
 */
public class UpdateSceneAttribute extends AbstractSceneCommand {

    /**
     * 角色id
     */
    private Role role;

    @Override
    public void action() {
        GetBean.getScenesService().updateSceneAttribute(role);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UpdateSceneAttribute(Role role) {
        this.role = role;
        setMapId(role.getMap());
    }
}
