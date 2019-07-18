package com.aiwan.server.world.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 地图转移命令
 *
 * @author dengzebiao
 * @since 2019.7.4
 */
public class ChangeMapCommand extends AbstractSceneCommand {

    Logger logger = LoggerFactory.getLogger(ChangeMapCommand.class);

    /**
     * 角色
     */
    private Role role;

    /**
     * 跳转地图id
     */
    private int targetMapId;

    @Override
    public void action() {
        GetBean.getScenesService().changeMap(role, targetMapId);
    }

    public ChangeMapCommand(Role role, int targetMapId) {
        super(role.getAccountId(), role.getMap());
        this.role = role;
        setTargetMapId(targetMapId);
        setTaskName("地图转移命令");
    }

    public int getTargetMapId() {
        return targetMapId;
    }

    public void setTargetMapId(int targetMapId) {
        this.targetMapId = targetMapId;
    }
}
