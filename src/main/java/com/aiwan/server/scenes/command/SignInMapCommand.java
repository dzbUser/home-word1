package com.aiwan.server.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.fight.pvpUnit.FighterRole;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;

/**
 * 玩家登陆时进入地图指令（无需初始化坐标）
 */
public class SignInMapCommand extends AbstractSceneCommand {

    /**
     * 角色
     */
    private Role role;

    @Override
    public void action() {
        role.setChangingMap(true);
        //把角色存到地图资源
        FighterRole fighterRole = FighterRole.valueOf(role);
        GetBean.getMapManager().putFighterRole(fighterRole);
        //给所有玩家发送消息
        GetBean.getMapManager().sendMessageToUsers(getKey());
        role.setChangingMap(false);
    }

    public SignInMapCommand(Role role) {
        super(role.getAccountId(), role.getMap());
        this.role = role;
        setTaskName("玩家登陆时进入地图指令");
    }
}
