package com.aiwan.server.user.role.team.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 申请加入队伍
 */
@ProtocolAnnotation(protocol = Protocol.APPLYJOIN)
public class CM_ApplyJoin implements Serializable {

    /**
     * 队伍id
     */
    private long teamId;

    public static CM_ApplyJoin valueOf(long teamId) {
        CM_ApplyJoin cm_applyJoin = new CM_ApplyJoin();
        cm_applyJoin.setTeamId(teamId);
        return cm_applyJoin;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }
}
