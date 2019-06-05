package com.aiwan.server.user.backpack.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;



import java.io.Serializable;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 查看背包协议
 * */
@ProtocolAnnotation(protocol = Protocol.VIEWBACKPACK)
public class CM_ViewBackpack implements Serializable {


    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public CM_ViewBackpack setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }
}
