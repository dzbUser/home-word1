package com.aiwan.server.user.role.team.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 允许加入队伍
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
@ProtocolAnnotation(protocol = Protocol.ALLOW_JOIN)
public class CM_AllowJoin implements Serializable {

    /**
     * 允许角色的d
     */
    private long allowId;

    public static CM_AllowJoin valueOf(long allowId) {
        CM_AllowJoin cm_allowJoin = new CM_AllowJoin();
        cm_allowJoin.setAllowId(allowId);
        return cm_allowJoin;
    }

    public long getAllowId() {
        return allowId;
    }

    public void setAllowId(long allowId) {
        this.allowId = allowId;
    }
}
