package com.aiwan.server.user.role.attributes.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dengzebiap
 * @since 2019.6.6
 * 查看用户属性协议
 * */
@ProtocolAnnotation(protocol = Protocol.VIEWATTRIBUTES)
public class CM_ViewAttributes implements Serializable {
    /** 账号id */
    private String accountId;

    /** 角色id */
    private int rId;

    public String getAccountId() {
        return accountId;
    }

    public CM_ViewAttributes setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }


    public int getrId() {
        return rId;
    }

    public CM_ViewAttributes setrId(int rId) {
        this.rId = rId;
        return this;
    }
}
