package com.aiwan.server.world.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.world.base.handler.ISceneHandler;
import com.aiwan.server.world.base.scene.AbstractScene;
import com.aiwan.server.world.base.scene.DungeonScene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 玩家登陆时进入地图指令（无需初始化坐标）
 */
public class SignInMapCommand extends AbstractSceneCommand {

    private final static Logger logger = LoggerFactory.getLogger(SignInMapCommand.class);

    /**
     * 角色
     */
    private Role role;

    @Override
    public void action() {
        try {
            role.setChangingMap(true);
            //把角色存到地图资源
            AbstractScene abstractScene = GetBean.getMapManager().getSceneObject(role.getMap(), role.getSceneId());
            if (abstractScene == null) {
                //不存在此资源
                role.setChangingMap(false);
                EnterMapCommand enterMapCommand = new EnterMapCommand(1, role, null);
                GetBean.getSceneExecutorService().submit(enterMapCommand);
                return;
            }
            //进入地图
            ISceneHandler handler = abstractScene.getHandler();
            handler.enterDungeon(role, null);
            role.setChangingMap(false);
        } catch (Exception e) {
            logger.error("{}跳转到{}失败", role.getId(), getMapId());
            //取消跳转
            role.setChangingMap(false);
            //直接进入主城
            EnterMapCommand enterMapCommand = new EnterMapCommand(1, role, null);
            GetBean.getSceneExecutorService().submit(enterMapCommand);
        }

    }

    public SignInMapCommand(Role role) {
        super(role.getAccountId(), role.getMap(), role.getSceneId());
        this.role = role;
        setTaskName("玩家登陆时进入地图指令");
    }

    @Override
    public String getTaskName() {
        return "SignInMapCommand";
    }
}
