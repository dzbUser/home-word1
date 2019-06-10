package com.aiwan.server.user.role.equipment;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * @since 2019.6.6
 * 查看装备栏
 * */
@ProtocolAnnotation(protocol = Protocol.VIEWQEUIP)
public class CM_ViewEquipBar implements Serializable {
    /** 用户账号 */
    private String accountId;

    /** 角色id */
    private Long rId;

    public String getAccountId() {
        return accountId;
    }

    public CM_ViewEquipBar setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public Long getrId() {
        return rId;
    }

    public CM_ViewEquipBar setrId(Long rId) {
        this.rId = rId;
        return this;
    }
}
