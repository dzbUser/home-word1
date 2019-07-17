package com.aiwan.server.user.role.team.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.team.manager.TeamManager;
import com.aiwan.server.user.role.team.model.TeamModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (role.getIsTeam()) {
            logger.debug("角色{}创建队伍失败，原因：已经在队伍中", role.getId());
            return;
        }
        TeamModel teamModel = TeamModel.valueOf(role.getId());
        teamManager.putTeam(teamModel);
    }
}
