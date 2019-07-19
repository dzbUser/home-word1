package com.aiwan.server.world.base.scene;

import com.aiwan.server.user.role.team.model.TeamModel;
import com.aiwan.server.world.base.handler.AbstractDungeonHandler;

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
     * 进入副本的队伍
     */
    private TeamModel teamModel;

    /**
     * 副本处理器
     */
    private AbstractDungeonHandler handler;


    /**
     * 击杀怪物后调用的监听
     */
    public void monsterKillListener() {
        handler.checkpointListener();
    }


    public TeamModel getTeamModel() {
        return teamModel;
    }

    public void setTeamModel(TeamModel teamModel) {
        this.teamModel = teamModel;
    }

    public AbstractDungeonHandler getHandler() {
        return handler;
    }

    public void setHandler(AbstractDungeonHandler handler) {
        this.handler = handler;
    }
}
