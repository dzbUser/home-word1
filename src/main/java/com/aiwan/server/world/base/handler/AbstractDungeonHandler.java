package com.aiwan.server.world.base.handler;

import com.aiwan.server.base.event.event.impl.DungeonClearanceEvent;
import com.aiwan.server.base.executor.scene.impl.AbstractSceneRateCommand;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.world.base.scene.DungeonScene;
import com.aiwan.server.world.dungeon.command.DungeonOverCommand;
import com.aiwan.server.world.scenes.command.SceneRateCommand;
import com.aiwan.server.world.scenes.mapresource.SettlementBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象副本处理器（底层）
 *
 * @author dengzebiao
 * @since 2019.7.24
 */
public abstract class AbstractDungeonHandler {

    Logger logger = LoggerFactory.getLogger(AbstractDungeonHandler.class);

    /**
     * 副本
     */
    private DungeonScene dungeonScene;

    /**
     * 发放结算奖励（发放奖励的方式不一样）
     */
    public abstract void settlementReward(SettlementBean settlementBean);

    /**
     * 初始化副本
     */
    public void init() {
        //添加定时器
        DungeonOverCommand dungeonOverCommand = new DungeonOverCommand(getDungeonScene().getResource().getDuration(), null, dungeonScene.getMapId(), dungeonScene.getSceneId(), getDungeonScene());
        getDungeonScene().getCommandMap().put(DungeonOverCommand.class, dungeonOverCommand);
        //添加场景扫描定时器
        SceneRateCommand sceneRateCommand = new SceneRateCommand(null, dungeonScene.getMapId(), dungeonScene.getSceneId(), 0, 1000);
        getDungeonScene().getCommandMap().put(SceneRateCommand.class, sceneRateCommand);
        //启动定时器
        GetBean.getSceneExecutorService().submit(sceneRateCommand);
        GetBean.getSceneExecutorService().submit(dungeonOverCommand);
        getDungeonScene().setCanEnter(true);
    }

    /**
     * 玩家进入副本
     */
    public abstract void enterDungeon(Role role, RoleUnit roleUnit);

    /**
     * 玩家退出副本
     */
    public abstract void quitDungeon(Role role);

    /**
     * 释放资源，包括地图资源、主题返回主城
     */
    public void releaseDungeon() {
        //取消循环检查处理器
        getDungeonScene().getCommandMap().get(SceneRateCommand.class).cancel();
        //取消副本定时器
        getDungeonScene().getCommandMap().get(DungeonOverCommand.class).cancel();
        //删除副本
        GetBean.getMapManager().removeSceObject(getDungeonScene().getMapId(), getDungeonScene().getSceneId());
    }

    /**
     * 结算,结算应包括奖励的发放、资源释放、事件的触发等
     */
    public abstract void settlement();

    /**
     * 发送通关事件
     *
     * @param role  角色
     * @param mapId 通关地图id
     */
    public void dungeonClearanceEvent(Role role, int mapId) {
        DungeonClearanceEvent dungeonClearanceEvent = DungeonClearanceEvent.valueOf(role, mapId);
        GetBean.getEventBusManager().synSubmit(dungeonClearanceEvent);
    }


    public DungeonScene getDungeonScene() {
        return dungeonScene;
    }

    public void setDungeonScene(DungeonScene dungeonScene) {
        this.dungeonScene = dungeonScene;
    }
}
