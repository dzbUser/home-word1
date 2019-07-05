package com.aiwan.server.user.role.buff.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 查看玩家身上的buff
 */
@ProtocolAnnotation(protocol = Protocol.VIEW_BUFF)
public class CM_ViewBuff implements Serializable {

    /**
     * 角色id
     */
    private Long rId;

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public static CM_ViewBuff valueOf(Long rId) {
        CM_ViewBuff cm_viewBuff = new CM_ViewBuff();
        cm_viewBuff.setrId(rId);
        return cm_viewBuff;
    }
}
