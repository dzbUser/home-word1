package com.aiwan.server.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;

/**
 * 离开地图命令
 *
 * @author dengzebiao
 * @since 2019.7.4
 */
public class LeaveMapCommand extends AbstractSceneCommand {

    /**
     * 角色
     */
    private Role role;

    @Override
    public void action() {
        //脱离地图
        GetBean.getMapManager().removeFighterRole(getKey(), role.getId());
        //给地图所有玩家发送最新地图信息
        GetBean.getMapManager().sendMessageToUsers(getKey());
    }

    public LeaveMapCommand(Role role) {
        this.role = role;
        setMapId(role.getMap());
    }
}
