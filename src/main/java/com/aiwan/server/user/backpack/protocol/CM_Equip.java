package com.aiwan.server.user.backpack.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

@ProtocolAnnotation(protocol = Protocol.EQUIP)
public class CM_Equip implements Serializable {
    /**
     * 用户账号
     */
    private String accountId;

    /**
     * 角色id
     */
    private Long rId;

    /**
     * 用户位置
     */
    private int position;

    public static CM_Equip valueOf(String accountId, Long rId, int position) {
        CM_Equip cm_equip = new CM_Equip();
        cm_equip.setAccountId(accountId);
        cm_equip.setrId(rId);
        cm_equip.setPosition(position);
        return cm_equip;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
