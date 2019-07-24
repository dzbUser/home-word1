package com.aiwan.server.user.role.team.command;

import com.aiwan.server.base.executor.account.impl.AbstractAccountCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.team.model.TeamModel;
import com.aiwan.server.util.GetBean;

public class BeKickOutCommand extends AbstractAccountCommand {

    /**
     * 主体
     */
    private Role role;

    /**
     * 队伍
     */
    private TeamModel teamModel;

    public BeKickOutCommand(Role role, TeamModel teamModel) {
        super(role.getAccountId());
        this.role = role;
        this.teamModel = teamModel;
    }

    @Override
    public void active() {
        GetBean.getTeamService().beKickOut(role, teamModel);
    }

    @Override
    public String getTaskName() {
        return "BeKickOutCommand";
    }
}
