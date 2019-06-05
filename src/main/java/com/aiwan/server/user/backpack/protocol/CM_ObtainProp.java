package com.aiwan.server.user.backpack.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dengzebia
 * @since 2019.6.5
 * 道具协议
 * */
@ProtocolAnnotation(protocol = Protocol.OBTAINPROP)
public class CM_ObtainProp implements Serializable {
    /** 账号id */
    private String accountId;

    /** 道具id */
    private int id;

    /** 数量 */
    private int num;

    public String getAccountId() {
        return accountId;
    }

    public CM_ObtainProp setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public int getId() {
        return id;
    }

    public CM_ObtainProp setId(int id) {
        this.id = id;
        return this;
    }

    public int getNum() {
        return num;
    }

    public CM_ObtainProp setNum(int num) {
        this.num = num;
        return this;
    }
}
