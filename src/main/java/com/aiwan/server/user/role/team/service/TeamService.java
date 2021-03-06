package com.aiwan.server.user.role.team.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.team.command.JoinTeamCommand;
import com.aiwan.server.user.role.team.command.BeKickOutCommand;
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
        if (teamManager.isInTeam(role.getId())) {
            logger.debug("角色{}创建队伍失败，原因：已经在队伍中", role.getId());
            session.sendPromptMessage(PromptCode.IN_TEAM, "");
            return;
        }
        TeamModel teamModel = TeamModel.valueOf(role);
        GetBean.getTeamManager().joinTeam(role.getId(), teamModel.getObjectId());
        GetBean.getTeamManager().putTeam(teamModel);
        session.sendPromptMessage(PromptCode.CREATE_TEAM_SUCCESS, "");
    }

    @Override
    public void viewAllTeam(long rId) {
        List<TeamMessage> list = new ArrayList<>();
        for (TeamModel teamModel : teamManager.getTeamModelMap().values()) {
            Role role = GetBean.getRoleManager().load(teamModel.getLeaderId());
            TeamMessage teamMessage = TeamMessage.valueOf(teamModel.getObjectId(), role.getName(), teamModel.getTeamList().size());
            list.add(teamMessage);
        }

        SM_ViewAllTeam sm_viewAllTeam = SM_ViewAllTeam.valueOf(list);
        SessionManager.sendMessageByRid(rId, StatusCode.VIEW_ALL_TEAM, sm_viewAllTeam);
    }

    @Override
    public void applyJoin(long teamId, long rId) {
        TeamModel teamModel = teamManager.getTeam(teamId);
        if (teamModel == null) {
            logger.error("{}申请加入队伍失败，没有该队伍", rId);
            SessionManager.sendPromptMessage(rId, PromptCode.NO_TEAM, "");
            return;
        }
        Role role = GetBean.getRoleManager().load(rId);
        if (teamManager.isInTeam(rId)) {
            logger.error("{}申请加入队伍失败，已经在队伍中", rId);
            SessionManager.sendPromptMessage(rId, PromptCode.HAVE_IN_TEAM, "");
            return;
        }
        if (teamModel.isTeamFull()) {
            logger.error("{}申请加入队伍失败，队伍已满", rId);
            SessionManager.sendPromptMessage(rId, PromptCode.TEAM_FULL, "");
            return;
        }
        teamModel.getApplicationList().add(role.getId());
        logger.error("{}申请加入队伍成功", rId);
        SessionManager.sendPromptMessage(rId, PromptCode.APPLY_JOIN_SUCCESS, "");
        SessionManager.sendPromptMessage(teamModel.getLeaderId(), PromptCode.APPLY_JOIN_TEAM, role.getName());
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
        //删除队伍中的角色id
        teamModel.leaveTeam(rId);
        logger.info("{}离开队伍成功", rId);
        SessionManager.sendPromptMessage(rId, PromptCode.LEAVE_SUCCESS, "");
        if (teamModel.getLeaderId() != rId) {
            SessionManager.sendPromptMessage(teamModel.getLeaderId(), PromptCode.MEMBER_LEAVE_TEAM, role.getName());
        }
    }

    @Override
    public void viewAllApplication(long rId) {
        if (!teamManager.isInTeam(rId)) {
            logger.error("{}查看申请列表失败，未在队伍中", rId);
            SessionManager.sendPromptMessage(rId, PromptCode.NO_IN_TEAM, "");
            return;
        }
        TeamModel teamModel = teamManager.getTeam(teamManager.getTeamIdByRid(rId));
        List<MemberMessage> list = new ArrayList<>();
        for (Long teamRoleId : teamModel.getApplicationList()) {
            Role role = GetBean.getRoleManager().load(teamRoleId);
            list.add(MemberMessage.valueOf(role.getId(), role.getName(), role.getLevel(), role.getJob()));
        }
        SessionManager.sendMessageByRid(rId, StatusCode.VIEW_APPLICATION, SM_ViewApplication.valueOf(list));
    }

    /**
     * 允许加入
     *
     * @param allowRId 加入者id
     * @param session
     */
    @Override
    public void allowJoin(long allowRId, Session session) {
        Role applyRole = GetBean.getRoleManager().load(allowRId);
        if (applyRole == null) {
            logger.error("{}允许{}加入队伍失败，申请者不存在", session.getrId(), allowRId);
            return;
        }
        if (!teamManager.isInTeam(session.getrId())) {
            logger.error("{}允许{}加入队伍失败，操作者不在队伍中", session.getrId(), allowRId);
            session.sendPromptMessage(PromptCode.NO_IN_TEAM, "");
            return;
        }
        TeamModel teamModel = teamManager.getTeam(teamManager.getTeamIdByRid(session.getrId()));
        if (teamModel.getLeaderId() != session.getrId()) {
            logger.error("{}允许{}加入队伍失败，允许者不是队长", session.getrId(), allowRId);
            session.sendPromptMessage(PromptCode.NO_TEAM_LEADER, "");
            return;
        }
        if (!teamModel.isInApplication(allowRId)) {
            logger.error("{}允许{}加入队伍失败，申请者不在申请队列中", session.getrId(), allowRId);
            return;
        }
        //抛到加入者线程
        GetBean.getAccountExecutorService().submit(new JoinTeamCommand(applyRole.getAccountId(), applyRole, teamModel));
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
        //抛出被踢出命令到主体线程
        GetBean.getAccountExecutorService().submit(new BeKickOutCommand(GetBean.getRoleManager().load(kickOutRId), teamModel));
        session.sendPromptMessage(PromptCode.KICK_OUT_SUCCESS, "");
    }

    /**
     * 被踢出队伍
     */
    public void beKickOut(Role kickOutRole, TeamModel teamModel) {
        if (!teamModel.isContainMember(kickOutRole.getId())) {
            logger.error("{}已不在队伍中", kickOutRole.getId());
            return;
        }
        //踢出队伍操作
        teamModel.leaveTeam(kickOutRole.getId());
        //发送被踢出队伍提示
        SessionManager.sendPromptMessage(kickOutRole.getId(), PromptCode.BE_KICKOUT_TEAM, "");
    }

    public void joinTeam(Role applyRole, TeamModel teamModel) {
        if (GetBean.getTeamManager().isInTeam(applyRole.getId())) {
            logger.error("{}允许{}加入队伍失败，加入者已在队伍中", teamModel.getLeaderId(), applyRole.getId());
            return;
        }
        if (teamModel.isFullOrJoin(applyRole)) {
            logger.error("{}允许{}加入队伍失败，队伍已满", teamModel.getLeaderId(), applyRole.getId());
            SessionManager.sendPromptMessage(teamModel.getLeaderId(), PromptCode.TEAM_FULL, "");
            return;
        }
        logger.info("{}允许{}加入队伍成功", teamModel.getLeaderId(), applyRole.getId());
        SessionManager.sendPromptMessage(applyRole.getId(), PromptCode.JOIN_TEAM_SUCCESS, "");
        SessionManager.sendPromptMessage(teamModel.getLeaderId(), PromptCode.JOIN_TEAM_SUCCESS, applyRole.getName());
    }

    /**
     * 发出邀请
     */
    public void sendInvitation(long activeId, long inviteId) {

        Role inviteRole = GetBean.getRoleManager().load(inviteId);
        //邀请者是否存在
        if (inviteRole == null) {
            logger.error("{}邀请{}失败，没有邀请的角色", activeId, inviteId);
            return;
        }
        if (!teamManager.isInTeam(activeId)) {
            logger.error("{}邀请{}失败，邀请者没在队伍中", activeId, inviteId);
            SessionManager.sendPromptMessage(activeId, PromptCode.NO_IN_TEAM, "");
            return;
        }
        if (teamManager.isInTeam(inviteId)) {
            logger.error("{}邀请{}失败，被邀请者已在队伍中", activeId, inviteId);
            SessionManager.sendPromptMessage(activeId, PromptCode.HAVE_IN_TEAM, "你邀请的人");
            return;
        }
        TeamModel teamModel = teamManager.getTeam(teamManager.getTeamIdByRid(activeId));
        //发送邀请通知到邀请者
        SessionManager.sendPromptMessage(inviteId, PromptCode.INVITE_PROMPT, teamModel.getObjectId() + "");
        //发送邀请成功
        SessionManager.sendPromptMessage(activeId, PromptCode.INVITE_SUCCESS, "");
    }

    /**
     * 接受邀请
     */
    public void acceptInvitation(Role role, long teamId) {

        //获取队伍
        TeamModel teamModel = teamManager.getTeam(teamId);
        if (teamModel == null) {
            logger.error("{}接受邀请，加入{}队伍失败，队伍不存在", role.getId(), teamId);
            SessionManager.sendPromptMessage(role.getId(), PromptCode.NO_TEAM, "");
            return;
        }

        //是否在队伍中
        if (teamManager.isInTeam(role.getId())) {
            logger.error("{}接受邀请，加入{}队伍失败，已经在队伍中", role.getId(), teamId);
            SessionManager.sendPromptMessage(role.getId(), PromptCode.HAVE_IN_TEAM, "");
            return;
        }
        //加入队伍
        if (teamModel.isFullOrJoin(role)) {
            logger.error("{}接受邀请，加入{}队伍失败，队伍已满", role.getId(), teamId);
            SessionManager.sendPromptMessage(role.getId(), PromptCode.TEAM_FULL, "");
            return;
        }
        SessionManager.sendPromptMessage(role.getId(), PromptCode.JOIN_TEAM_SUCCESS, "");
        SessionManager.sendPromptMessage(teamModel.getLeaderId(), PromptCode.JOIN_TEAM_SUCCESS, role.getName());
    }

}
