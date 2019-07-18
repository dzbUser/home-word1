package com.aiwan.server.world.base.scene;

import com.aiwan.server.user.role.team.model.TeamModel;
import com.aiwan.server.world.base.process.DungeonHandler;

/**
 * 副本场景
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
public class DungeonScene extends AbstractScene {

    public DungeonScene(int mapId, int sceneId) {
        super(mapId, sceneId);
    }

    /**
     * 进入副本的队伍
     */
    private TeamModel teamModel;

    /**
     * 副本处理器
     */
    private DungeonHandler dungeonHandler;


    public TeamModel getTeamModel() {
        return teamModel;
    }

    public void setTeamModel(TeamModel teamModel) {
        this.teamModel = teamModel;
    }
}
