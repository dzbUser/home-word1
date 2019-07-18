package com.aiwan.server.user.role.team.facade;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.team.protocol.*;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * 队伍门面
 */
@Controller
public class TeamFacade {

    Logger logger = LoggerFactory.getLogger(TeamFacade.class);

    /**
     * 创建队伍
     */
    public void createTeam(CM_CreateTeam cm_createTeam, Session session) {
        if (session.getrId() == null) {
            logger.debug("错误包");
            return;
        }
        Role role = GetBean.getRoleManager().load(session.getrId());
        GetBean.getTeamService().createTeam(role);
    }

    /**
     * 创建队伍
     */
    public void viewAllTeam(CM_ViewAllTeam cm_viewAllTeam, Session session) {
        if (session.getrId() == null) {
            logger.debug("错误包");
            return;
        }
        Role role = GetBean.getRoleManager().load(session.getrId());
        GetBean.getTeamService().viewAllTeam(session);
    }

    /**
     * 离开队伍
     */
    public void leaveTeam(CM_LeaveTeam leaveTeam, Session session) {
        if (session.getrId() == null) {
            logger.debug("错误包");
            return;
        }
        GetBean.getTeamService().leaveTeam(session);
    }

    /**
     * 申请加入队伍
     */
    public void applyJoin(CM_ApplyJoin cm_applyJoin, Session session) {
        if (session.getrId() == null) {
            logger.debug("错误包");
            return;
        }
        GetBean.getTeamService().applyJoin(cm_applyJoin.getTeamId(), session);
    }

    /**
     * 查看申请列表
     */
    public void viewApplication(CM_ViewApplication cm_viewApplication, Session session) {
        if (session.getrId() == null) {
            logger.debug("错误包");
            return;
        }
        GetBean.getTeamService().viewAllApplication(session);
    }

    /**
     * 允许加入
     */
    public void allowJoin(CM_AllowJoin cm_allowJoin, Session session) {
        if (session.getrId() == null) {
            logger.debug("错误包");
            return;
        }
        GetBean.getTeamService().allowJoin(cm_allowJoin.getAllowId(), session);
    }

    /**
     * 查看队伍成员
     */
    public void viewMembers(CM_ViewMembers cm_viewMembers, Session session) {
        if (session.getrId() == null) {
            logger.debug("错误包");
            return;
        }
        GetBean.getTeamService().viewMemberInTeam(session);
    }

    /**
     * 查看队伍成员
     */
    public void kickOut(CM_KickOutTeam cm_kickOutTeam, Session session) {
        if (session.getrId() == null) {
            logger.debug("错误包");
            return;
        }
        GetBean.getTeamService().kickOut(cm_kickOutTeam.getKickOutRid(), session);
    }

}