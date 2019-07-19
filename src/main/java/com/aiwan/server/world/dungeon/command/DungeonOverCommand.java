package com.aiwan.server.world.dungeon.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneDelayCommand;
import com.aiwan.server.world.base.scene.DungeonScene;

/**
 * 副本时间到命令
 *
 * @author dengzebiao
 * @since 2019.7.19
 */
public class DungeonOverCommand extends AbstractSceneDelayCommand {

    private DungeonScene dungeonScene;

    public DungeonOverCommand(long delay, String accountId, int mapId, int sceneId, DungeonScene dungeonScene) {
        super(delay, accountId, mapId, sceneId);
        this.dungeonScene = dungeonScene;
    }

    @Override
    public void action() {
        //副本时间到，结算副本
        dungeonScene.getHandler().settlement();
    }
}
