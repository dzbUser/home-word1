package com.aiwan.server.user.role.team.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

/**
 * 接受队伍邀请
 *
 * @author dengzebiao
 * @since 2019.7.24
 */
@ProtocolAnnotation(protocol = Protocol.ACCEPT_TEAM_INVITE)
public class CM_AcceptTeamInvite {

    /**
     * 队伍id
     */
    private long teamId;

    public static CM_AcceptTeamInvite valueOf(long teamId) {
        CM_AcceptTeamInvite cm_acceptTeamInvite = new CM_AcceptTeamInvite();
        cm_acceptTeamInvite.setTeamId(teamId);
        return cm_acceptTeamInvite;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

}
