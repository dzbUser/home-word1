package com.aiwan.server.role.player.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * 获取角色新信息的协议类
 * */
@ProtocolAnnotation(protocol = Protocol.GETROLEMESSAGE)
public class CM_RoleMessage implements Serializable {
    private String accountId;
    private Long rId;

    public String getAccountId() {
        return accountId;
    }

    public CM_RoleMessage setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public Long getrId() {
        return rId;
    }

    public CM_RoleMessage setrId(Long rId) {
        this.rId = rId;
        return this;
    }
}
