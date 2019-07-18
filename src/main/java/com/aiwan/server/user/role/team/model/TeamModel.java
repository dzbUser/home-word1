package com.aiwan.server.user.role.team.model;

import com.aiwan.server.publicsystem.model.GameObject;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.IDUtil;

import java.util.*;

/**
 * 队伍模型
 *
 * @author dengzebiao
 * @since 2019.7.17
 */
public class TeamModel extends GameObject {

    /**
     * 最大人数
     */
    public static int MAXNUM = 5;

    /**
     * 队长ID
     */
    private long leaderId;

    /**
     * 队伍列表
     */
    private List<Role> teamList = Collections.synchronizedList(new ArrayList<>());

    /**
     * 申请列表
     */
    private Set<Long> applicationList = Collections.synchronizedSet(new LinkedHashSet<>());

    public static TeamModel valueOf(Role role) {
        TeamModel teamModel = new TeamModel();
        teamModel.leaderId = role.getId();
        teamModel.setObjectId(IDUtil.getId());
        teamModel.getTeamList().add(role);
        return teamModel;
    }

    /**
     * 删除队伍id
     */
    public void removeRoleByRid(long rId) {
        for (Iterator<Role> it = teamList.iterator(); it.hasNext(); ) {
            Role role = it.next();
            if (role.getId() == rId) {
                it.remove();
                break;
            }
        }
    }

    /**
     * 队伍是否已满
     *
     * @return
     */
    public boolean isTeamFull() {
        if (getTeamList().size() == MAXNUM) {
            return true;
        }
        return false;
    }

    /**
     * 是否在申请队列中
     *
     * @param rId id
     * @return
     */
    public boolean isInApplication(long rId) {
        return applicationList.contains(rId);
    }

    /**
     * 是否有成员
     *
     * @param rId 成员id
     * @return
     */
    public boolean isContainMember(long rId) {
        for (Iterator<Role> it = teamList.iterator(); it.hasNext(); ) {
            Role role = it.next();
            if (role.getId() == rId) {
                return true;
            }
        }
        return false;
    }

    public long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(long leaderId) {
        this.leaderId = leaderId;
    }

    public List<Role> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Role> teamList) {
        this.teamList = teamList;
    }

    public Set<Long> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(Set<Long> applicationList) {
        this.applicationList = applicationList;
    }
}
