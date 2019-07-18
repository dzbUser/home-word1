package com.aiwan.server.user.role.team.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 踢出队伍
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
@ProtocolAnnotation(protocol = Protocol.KICK_OUT_TEAM)
public class CM_KickOutTeam implements Serializable {

    /**
     * 踢出成员的id
     */
    private long kickOutRid;

    public static CM_KickOutTeam valueOf(long kickOutRid) {
        CM_KickOutTeam cm_kickOutTeam = new CM_KickOutTeam();
        cm_kickOutTeam.setKickOutRid(kickOutRid);
        return cm_kickOutTeam;
    }

    public long getKickOutRid() {
        return kickOutRid;
    }

    public void setKickOutRid(long kickOutRid) {
        this.kickOutRid = kickOutRid;
    }
}
