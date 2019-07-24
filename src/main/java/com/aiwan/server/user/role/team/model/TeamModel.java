package com.aiwan.server.user.role.team.model;

import com.aiwan.server.publicsystem.model.GameObject;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.IDUtil;
import com.aiwan.server.util.PromptCode;

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

    /**
     * 邀请列表
     */
    private Set<Long> inviteList = Collections.synchronizedSet(new LinkedHashSet<>());

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

    public synchronized void leaveTeam(long rId) {
        removeRoleByRid(rId);
        GetBean.getTeamManager().leaveTeam(rId);
        //队伍中已没有成员
        if (teamList.size() == 0) {
            GetBean.getTeamManager().removeTeam(getObjectId());
        } else {
            //队长离开,需加锁，必须保证有一个队长在队伍中
            if (getLeaderId() == rId) {
                setLeaderId(getTeamList().get(0).getId());
                SessionManager.sendPromptMessage(rId, PromptCode.BECAME_LEADER, "");
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

    public synchronized boolean isFullOrJoin(Role role) {
        //锁着当前类
        if (isTeamFull()) {
            return true;
        }
        teamList.add(role);
        GetBean.getTeamManager().joinTeam(role.getId(), this.getObjectId());
        //删除申请队列中的申请者
        getApplicationList().remove(role.getId());
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

    public Set<Long> getInviteList() {
        return inviteList;
    }

    public void setInviteList(Set<Long> inviteList) {
        this.inviteList = inviteList;
    }

    /**
     * 添加到邀请队列
     *
     * @param inviteId
     */
    public void addInvitation(long inviteId) {
        inviteList.add(inviteId);
    }

    /**
     * 是否在邀请队列中
     *
     * @param rId
     * @return
     */
    public boolean isInInvitation(Long rId) {
        return inviteList.contains(rId);
    }

    /**
     * 删除邀请
     *
     * @param rId
     */
    public void removeInvite(Long rId) {
        inviteList.remove(rId);
    }
}
