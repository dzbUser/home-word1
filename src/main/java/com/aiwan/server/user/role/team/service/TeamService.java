package com.aiwan.server.user.role.team.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.team.manager.TeamManager;
import com.aiwan.server.user.role.team.model.TeamModel;
import com.aiwan.server.user.role.team.protocol.*;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import com.aiwan.server.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 队伍业务类
 *
 * @author dengzebiao
 * @since 2019.7.17
 */
@Service
public class TeamService implements ITeamService {


    Logger logger = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    private TeamManager teamManager;

    @Override
    public void createTeam(Role role) {
        Session session = SessionManager.getSessionByAccountId(role.getAccountId());
        if (teamManager.isInTeamOrCreate(role.getId())) {
            logger.debug("角色{}创建队伍失败，原因：已经在队伍中", role.getId());
            session.sendPromptMessage(PromptCode.IN_TEAM, "");
            return;
        }
        session.sendPromptMessage(PromptCode.CREATE_TEAM_SUCCESS, "");
    }

    @Override
    public void viewAllTeam(Session session) {
        List<TeamMessage> list = new ArrayList<>();
        for (TeamModel teamModel : teamManager.getTeamModelMap().values()) {
            Role role = GetBean.getRoleManager().load(teamModel.getLeaderId());
            TeamMessage teamMessage = TeamMessage.valueOf(teamModel.getObjectId(), role.getName(), teamModel.getTeamList().size());
            list.add(teamMessage);
        }

        SM_ViewAllTeam sm_viewAllTeam = SM_ViewAllTeam.valueOf(list);
        session.messageSend(StatusCode.VIEW_ALL_TEAM, sm_viewAllTeam);
    }

    @Override
    public void applyJoin(long teamId, Session session) {
        TeamModel teamModel = teamManager.getTeam(teamId);
        if (teamModel == null) {
            logger.error("{}申请加入队伍失败，没有该队伍", session.getrId());
            session.sendPromptMessage(PromptCode.NO_TEAM, "");
            return;
        }
        Role role = GetBean.getRoleManager().load(session.getrId());
        if (teamManager.isInTeam(session.getrId())) {
            logger.error("{}申请加入队伍失败，已经在队伍中", session.getrId());
            session.sendPromptMessage(PromptCode.HAVE_IN_TEAM, "");
            return;
        }
        if (teamModel.getTeamList().size() == TeamModel.MAXNUM) {
            logger.error("{}申请加入队伍失败，队伍已满", session.getrId());
            session.sendPromptMessage(PromptCode.TEAM_FULL, "");
            return;
        }
        teamModel.getApplicationList().add(role.getId());
        logger.error("{}申请加入队伍成功", session.getrId());
        session.sendPromptMessage(PromptCode.APPLY_JOIN_SUCCESS, "");
    }

    @Override
    public void leaveTeam(long rId) {
        Role role = GetBean.getRoleManager().load(rId);
        if (!teamManager.isInTeam(rId)) {
            logger.error("{}离开队伍失败，未在队伍中", rId);
            SessionManager.sendPromptMessage(rId, PromptCode.NO_IN_TEAM, "");
            return;
        }
        TeamModel teamModel = teamManager.getTeam(teamManager.getTeamIdByRid(rId));
        if (teamModel == null) {
            logger.error("{}离开队伍失败，错误包，所发队伍id找不到相应队伍", rId);
            SessionManager.sendPromptMessage(rId, PromptCode.NO_TEAM, "");
            return;
        }
        //删除队伍中的角色id
        teamModel.removeRoleByRid(rId);
        if (teamModel.getTeamList().size() == 0) {
            //队伍为空
            teamManager.removeTeam(teamModel.getObjectId());
        } else {
            //队长离开
            if (teamModel.getLeaderId() == rId) {
                teamModel.setLeaderId(teamModel.getTeamList().get(0).getId());
                SessionManager.sendPromptMessage(teamModel.getLeaderId(), PromptCode.BECAME_LEADER, "");
            }
        }
        teamManager.leaveTeam(rId);
        logger.info("{}离开队伍成功", rId);
        SessionManager.sendPromptMessage(rId, PromptCode.LEAVE_SUCCESS, "");
        if (teamModel.getLeaderId() != rId) {
            SessionManager.sendPromptMessage(teamModel.getLeaderId(), PromptCode.MEMBER_LEAVE_TEAM, role.getName());
        }
    }

    @Override
    public void viewAllApplication(Session session) {
        if (!teamManager.isInTeam(session.getrId())) {
            logger.error("{}查看申请列表失败，未在队伍中", session.getrId());
            session.sendPromptMessage(PromptCode.NO_IN_TEAM, "");
            return;
        }
        TeamModel teamModel = teamManager.getTeam(teamManager.getTeamIdByRid(session.getrId()));
        List<MemberMessage> list = new ArrayList<>();
        for (Long rId : teamModel.getApplicationList()) {
            Role role = GetBean.getRoleManager().load(rId);
            list.add(MemberMessage.valueOf(role.getId(), role.getName(), role.getLevel(), role.getJob()));
        }
        session.messageSend(StatusCode.VIEW_APPLICATION, SM_ViewApplication.valueOf(list));
    }

    /**
     * 允许加入
     *
     * @param allowRId
     * @param session
     */
    @Override
    public void allowJoin(long allowRId, Session session) {
        Role applyRole = GetBean.getRoleManager().load(allowRId);
        if (applyRole == null) {
            logger.error("{}允许{}加入队伍失败，申请者不存在", session.getrId(), allowRId);
            return;
        }
//        if (!teamManager.isInTeam(session.getrId())) {
//            logger.error("{}允许{}加入队伍失败，操作者不在队伍中", session.getrId(), allowRId);
//            session.sendPromptMessage(PromptCode.NO_IN_TEAM, "");
//            return;
//        }
        TeamModel teamModel = teamManager.getTeam(teamManager.getTeamIdByRid(session.getrId()));
        if (teamModel == null) {
            logger.error("{}允许{}加入队伍失败，操作者者队伍不存在", session.getrId(), allowRId);
            session.sendPromptMessage(PromptCode.NO_TEAM, "");
            return;
        }
        if (!teamModel.isInApplication(allowRId)) {
            logger.error("{}允许{}加入队伍失败，申请者不在申请队列中", session.getrId(), allowRId);
            return;
        }
        if (teamModel.getLeaderId() != session.getrId()) {
            logger.error("{}允许{}加入队伍失败，允许者不是队长", session.getrId(), allowRId);
            session.sendPromptMessage(PromptCode.NO_TEAM_LEADER, "");
            return;
        }
        if (teamModel.isTeamFull()) {
            logger.error("{}允许{}加入队伍失败，队伍已满", session.getrId(), allowRId);
            session.sendPromptMessage(PromptCode.TEAM_FULL, "");
            return;
        }
        //加入队伍
        if (teamManager.isInTeamOrJoin(allowRId, teamModel.getObjectId())) {
            logger.error("{}允许{}加入队伍失败，申请者已在其他队伍中", session.getrId(), allowRId);
            session.sendPromptMessage(PromptCode.HAVE_IN_TEAM, applyRole.getName());
            return;
        }
        teamModel.getTeamList().add(applyRole);
        teamModel.getApplicationList().remove(allowRId);
        logger.info("{}允许{}加入队伍成功", session.getrId(), allowRId);
        SessionManager.sendPromptMessage(allowRId, PromptCode.JOIN_TEAM_SUCCESS, "");
    }

    @Override
    public void viewMemberInTeam(Session session) {
        if (!teamManager.isInTeam(session.getrId())) {
            logger.error("{}查看队伍成员失败，不在队伍中", session.getrId());
            return;
        }
        TeamModel teamModel = teamManager.getTeam(teamManager.getTeamIdByRid(session.getrId()));

        List<MemberMessage> list = new ArrayList<>();
        for (Role role : teamModel.getTeamList()) {
            list.add(MemberMessage.valueOf(role.getId(), role.getName(), role.getLevel(), role.getJob()));
        }
        session.messageSend(StatusCode.VIEW_MEMBERS, SM_ViewMemberInTeam.valueOf(teamModel.getLeaderId(), list));
    }

    @Override
    public void kickOut(long kickOutRId, Session session) {
        if (kickOutRId == session.getrId()) {
            logger.error("{}踢出队伍成员失败，不可以把自己踢出队伍", kickOutRId);
            session.sendPromptMessage(PromptCode.NO_KICK_OUT_YOUSELF, "");
            return;
        }
        if (!teamManager.isInTeam(session.getrId())) {
            logger.error("{}踢出队伍成员失败，{}不在队伍中", kickOutRId, session.getrId());
            session.sendPromptMessage(PromptCode.NO_IN_TEAM, "");
            return;
        }
        TeamModel teamModel = teamManager.getTeam(teamManager.getTeamIdByRid(session.getrId()));
        if (!teamModel.isContainMember(kickOutRId)) {
            logger.error("{}踢出队伍成员失败，踢出对象不在队伍中", kickOutRId);
            session.sendPromptMessage(PromptCode.NO_IN_YOURTEAM, "");
            return;
        }
        if (teamModel.getLeaderId() != session.getrId()) {
            logger.error("{}踢出队伍成员失败，操作者不是队长", kickOutRId);
            session.sendPromptMessage(PromptCode.NO_TEAM_LEADER, "");
            return;
        }
        //踢出队伍操作
        teamManager.leaveTeam(kickOutRId);
        teamModel.removeRoleByRid(kickOutRId);
        session.sendPromptMessage(PromptCode.KICK_OUT_SUCCESS, "");
    }


}
