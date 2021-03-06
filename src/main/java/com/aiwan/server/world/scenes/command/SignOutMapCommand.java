package com.aiwan.server.world.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.world.base.handler.ISceneHandler;
import com.aiwan.server.world.base.scene.AbstractScene;
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
        //去除地图缓存
        AbstractScene abstractScene = GetBean.getMapManager().getSceneObject(role.getMap(), role.getSceneId());
        ISceneHandler handler = abstractScene.getHandler();
        handler.quitDungeon(role);
    }

    public SignOutMapCommand(Role role) {
        super(role.getAccountId(), role.getMap());
        this.role = role;
        setTaskName("离开地图指令");
    }

    @Override
    public String getTaskName() {
        return "SignOutMapCommand";
    }
}