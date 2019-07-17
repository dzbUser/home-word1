package com.aiwan.server.user.role.team.manager;

import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.user.role.team.model.TeamModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 队伍管理类
 *
 * @author dengzebiao
 * @since 2019.7.17
 */

@Manager
public class TeamManager {

    /**
     * 用来存储所有队伍，容器必须保证线程安全
     */
    private Map<Long, TeamModel> teamModelMap = new ConcurrentHashMap<>();

    /**
     * 添加队伍
     *
     * @param teamModel 队伍
     */
    public void putTeam(TeamModel teamModel) {
        teamModelMap.put(teamModel.getObjectId(), teamModel);
    }

    /**
     * 移除队伍
     *
     * @param id 队伍id
     */
    public void removeTeam(long id) {
        teamModelMap.remove(id);
    }
}
