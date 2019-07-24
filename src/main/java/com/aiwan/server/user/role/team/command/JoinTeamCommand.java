package com.aiwan.server.user.role.team.command;

import com.aiwan.server.base.executor.account.impl.AbstractAccountCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.team.model.TeamModel;
import com.aiwan.server.util.GetBean;

/**
 * 加入队伍指令
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class JoinTeamCommand extends AbstractAccountCommand {

    /**
     * 加入队伍角色
     */
    private Role role;

    /**
     * 加入队伍
     */
    private TeamModel teamModel;

    public JoinTeamCommand(String accountId, Role role, TeamModel teamModel) {
        super(accountId);
        this.role = role;
        this.teamModel = teamModel;
    }

    @Override
    public void active() {
        GetBean.getTeamService().joinTeam(role, teamModel);
    }

    @Override
    public String getTaskName() {
        return "JoinTeamCommand";
    }
}
