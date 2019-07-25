package com.aiwan.server.world.base.scene;

import com.aiwan.server.user.role.team.model.TeamModel;
import com.aiwan.server.world.base.handler.AbstractChapterDungeonHandler;

/**
 * 副本场景
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
public class DungeonScene extends AbstractScene {

    public DungeonScene(int mapId) {
        super(mapId, GLOBAL_SCENE_ID.getAndDecrement());
    }

    /**
     * 副本处理器
     */
    private AbstractChapterDungeonHandler handler;


    /**
     * 击杀怪物后调用的监听
     */
    public void monsterKillListener() {
        handler.checkpointListener();
    }


    public AbstractChapterDungeonHandler getHandler() {
        return handler;
    }

    public void setHandler(AbstractChapterDungeonHandler handler) {
        this.handler = handler;
    }

}
