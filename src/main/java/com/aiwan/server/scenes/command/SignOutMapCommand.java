package com.aiwan.server.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 离开地图指令
 *
 * @author dengzebiao
 * @since 2019.7.5
 */
public class SignOutMapCommand extends AbstractSceneCommand {

    private Logger logger = LoggerFactory.getLogger(SignOutMapCommand.class);


    /**
     * 移动角色
     */
    private Role role;


    @Override
    public void action() {
        if (role.isChangingMap()) {
            logger.info("角色:{},正在进行地图跳转", role.getId());
            return;
        }
        GetBean.getScenesService().leaveMap(role);
    }

    public SignOutMapCommand(Role role) {
        this.role = role;
        setMapId(role.getMap());
    }
}