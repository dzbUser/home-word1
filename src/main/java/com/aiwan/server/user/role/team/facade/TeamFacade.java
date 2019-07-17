package com.aiwan.server.user.role.team.facade;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.team.protocol.CM_CreateTeam;
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
    private void createTeam(CM_CreateTeam cm_createTeam, Session session) {
        if (session.getrId() == null) {
            logger.debug("错误包");
            return;
        }
        Role role = GetBean.getRoleManager().load(session.getrId());
        GetBean.getTeamService().createTeam(role);
    }

}
