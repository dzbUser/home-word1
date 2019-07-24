package com.aiwan.server.user.role.team.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 邀请加入队伍
 *
 * @author dengzebiao
 * @since 2019.7.24
 */
@ProtocolAnnotation(protocol = Protocol.INVITE_TEAM_JOIN)
public class CM_InviteJoinTeam implements Serializable {

    /**
     * 邀请者id
     */
    private long inviteId;

    public static CM_InviteJoinTeam valueOf(long inviteId) {
        CM_InviteJoinTeam cm_inviteJoinTeam = new CM_InviteJoinTeam();
        cm_inviteJoinTeam.setInviteId(inviteId);
        return cm_inviteJoinTeam;
    }

    public long getInviteId() {
        return inviteId;
    }

    public void setInviteId(long inviteId) {
        this.inviteId = inviteId;
    }
}
