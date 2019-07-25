package com.aiwan.server.user.role.team.manager;

import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.team.model.TeamModel;
import com.aiwan.server.util.GetBean;

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
     * 存角色id与队伍id的映射
     */
    private Map<Long, Long> teamRoleMap = new ConcurrentHashMap<>();

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

    /**
     * 获取队伍
     *
     * @param id id
     * @return
     */
    public TeamModel getTeam(long id) {
        return teamModelMap.get(id);
    }

    /**
     * 是否在队伍中
     *
     * @param rId 角色id
     * @return
     */
    public boolean isInTeam(long rId) {
        if (teamRoleMap.get(rId) != null) {
            return true;
        }
        return false;
    }


    /**
     * 加入队伍
     *
     * @param rId    角色id
     * @param teamId 队伍id
     */
    public void joinTeam(long rId, long teamId) {
        teamRoleMap.put(rId, teamId);
    }

    /**
     * 离开队伍
     *
     * @return
     */
    public void leaveTeam(long rId) {
        teamRoleMap.remove(rId);
    }

    /**
     * 两个角色是不是队友
     */
    public boolean isTeamMate(long rId1, long rId2) {
        if (getTeamIdByRid(rId1) == null || getTeamIdByRid(rId2) == null) {
            return false;
        }
        return getTeamIdByRid(rId1).equals(getTeamIdByRid(rId2));
    }

    public Long getTeamIdByRid(long rId) {
        return teamRoleMap.get(rId);
    }

    public Map<Long, TeamModel> getTeamModelMap() {
        return teamModelMap;
    }

    public void setTeamModelMap(Map<Long, TeamModel> teamModelMap) {
        this.teamModelMap = teamModelMap;
    }


}
