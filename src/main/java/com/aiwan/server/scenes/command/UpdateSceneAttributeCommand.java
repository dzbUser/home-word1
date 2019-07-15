package com.aiwan.server.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 更新场景中的角色属性
 */
public class UpdateSceneAttributeCommand extends AbstractSceneCommand {

    private Logger logger = LoggerFactory.getLogger(UpdateSceneAttributeCommand.class);

    /**
     * 角色id
     */
    private Role role;

    @Override
    public void action() {
        if (role.isChangingMap()) {
            logger.info("角色:{},正在进行地图跳转,更新属性失败", role.getId());
            return;
        }
        GetBean.getScenesService().updateSceneAttribute(role);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UpdateSceneAttributeCommand(Role role) {
        super(role.getAccountId(), role.getMap());
        this.role = role;
        setTaskName("更新场景中的角色属性命令");
    }
}
