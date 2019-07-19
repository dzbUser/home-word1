package com.aiwan.server.world.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.world.base.scene.AbstractScene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 玩家登陆时进入地图指令（无需初始化坐标）
 */
public class SignInMapCommand extends AbstractSceneCommand {

    Logger logger = LoggerFactory.getLogger(SignInMapCommand.class);

    /**
     * 角色
     */
    private Role role;

    @Override
    public void action() {
        role.setChangingMap(true);
        //把角色存到地图资源
        AbstractScene abstractScene = GetBean.getMapManager().getSceneObject(role.getMap());
        if (abstractScene == null) {
            //不存在此资源
            role.setChangingMap(false);
            EnterMapCommand enterMapCommand = new EnterMapCommand(1, role, null);
            GetBean.getSceneExecutorService().submit(enterMapCommand);
            return;
        }
        RoleUnit roleUnit = RoleUnit.valueOf(role, abstractScene.getMapId());
        abstractScene.putBaseUnit(roleUnit);
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
