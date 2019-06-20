package com.aiwan.server.prop.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 道具使用
 * @author dengzebiao
 * */
@ProtocolAnnotation(protocol = Protocol.PROPUSER)
public class CM_PropUse implements Serializable {
    /** 用户id */
    private String accountId;

    /** 角色id */
    private Long rId;

    /**
     * 道具位置
     */
    private int position;

    public String getAccountId() {
        return accountId;
    }

    public CM_PropUse setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public Long getrId() {
        return rId;
    }

    public CM_PropUse setrId(Long rId) {
        this.rId = rId;
        return this;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
