package com.aiwan.server.role.mount.protocol;


import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dengzebia
 * @since 2019.6.6
 * 坐骑查看协议
 * */
@ProtocolAnnotation(protocol = Protocol.VIEWMOUNT)
public class CM_ViewMount implements Serializable {
    /** 用户账号 */
    private String accountId;

    /** 角色id */
    private Long rId;

    public String getAccountId() {
        return accountId;
    }

    public CM_ViewMount setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public Long getrId() {
        return rId;
    }

    public CM_ViewMount setrId(Long rId) {
        this.rId = rId;
        return this;
    }
}
