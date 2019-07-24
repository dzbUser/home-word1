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

    /**
     * 跳转场景id,为0代表是永久地图
     */
    private int targetSceneId;

    @Override
    public void action() {
        GetBean.getScenesService().changeMap(role, targetMapId, targetSceneId);
    }

    public ChangeMapCommand(Role role, int targetMapId) {
        super(role.getAccountId(), role.getMap());
        this.role = role;
        setTargetMapId(targetMapId);
        setTargetSceneId(0);
        setTaskName("地图转移命令");
    }

    public ChangeMapCommand(Role role, int targetMapId, int targetSceneId) {
        super(role.getAccountId(), role.getMap(), role.getSceneId());
        this.role = role;
        setTargetMapId(targetMapId);
        setTargetSceneId(targetSceneId);
        setTaskName("地图转移命令");
    }

    public int getTargetMapId() {
        return targetMapId;
    }

    public void setTargetMapId(int targetMapId) {
        this.targetMapId = targetMapId;
    }

    public int getTargetSceneId() {
        return targetSceneId;
    }

    public void setTargetSceneId(int targetSceneId) {
        this.targetSceneId = targetSceneId;
    }

    @Override
    public String getTaskName() {
        return "ChangeMapCommand";
    }
}
