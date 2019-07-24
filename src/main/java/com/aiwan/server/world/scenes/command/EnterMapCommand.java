package com.aiwan.server.world.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.world.base.scene.AbstractScene;
import com.aiwan.server.world.base.scene.DungeonScene;
import com.aiwan.server.world.scenes.mapresource.MapResource;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 进入地图命令(需要初始化坐标)
 *
 * @author dengzebiao
 * @since 2019.7.4
 */
public class EnterMapCommand extends AbstractSceneCommand {

    private final static Logger logger = LoggerFactory.getLogger(EnterMapCommand.class);

    /**
     * 角色
     */
    private Role role;

    /**
     * 原先战斗单元
     */
    private RoleUnit roleUnit;


    @Override
    public void action() {
        try {
            AbstractScene abstractScene = GetBean.getMapManager().getSceneObject(getMapId(), getSceneId());
            if (abstractScene instanceof DungeonScene) {
                //副本
                DungeonScene dungeonScene = (DungeonScene) abstractScene;
                dungeonScene.getHandler().enterDungeon(role, roleUnit);
            } else {
                //普通地图
                GetBean.getScenesService().enterMap(role, abstractScene, roleUnit);
            }
        } catch (Exception e) {
            logger.error("{}跳转到{}失败", role.getId(), getMapId());
            //取消跳转
            role.setChangingMap(false);
            //直接进入主城
            EnterMapCommand enterMapCommand = new EnterMapCommand(1, role, null);
            GetBean.getSceneExecutorService().submit(enterMapCommand);

        }
    }

    public EnterMapCommand(int mapId, Role role, RoleUnit roleUnit) {
        super(role.getAccountId(), mapId);
        setTaskName("进入地图命令");
        this.role = role;
        this.roleUnit = roleUnit;
    }

    public EnterMapCommand(int mapId, int sceneId, Role role, RoleUnit roleUnit) {
        super(role.getAccountId(), mapId, sceneId);
        setTaskName("进入地图命令");
        this.role = role;
        this.roleUnit = roleUnit;
    }

    @Override
    public String getTaskName() {
        return "EnterMapCommand";
    }
}
