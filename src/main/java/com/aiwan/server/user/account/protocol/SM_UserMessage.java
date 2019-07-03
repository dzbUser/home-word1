package com.aiwan.server.user.account.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息发送协议
 * @author dengzebiao
 * */
public class SM_UserMessage implements Serializable {
    /** 登录状态*/
    private boolean status;
    /** 用户账号 */
    private String accountId;
    //是否创建角色
    private boolean created = true;
    //其他信息
    private String otherMessage;

    List<Long> roles;


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }


    public boolean isCreated() {
        return created;
    }

    public SM_UserMessage setCreated(boolean created) {
        this.created = created;
        return this;
    }

    public String getOtherMessage() {
        return otherMessage;
    }

    public SM_UserMessage setOtherMessage(String otherMessage) {
        this.otherMessage = otherMessage;
        return this;
    }

    public List<Long> getRoles() {
        return roles;
    }

    public SM_UserMessage setRoles(List<Long> roles) {
        this.roles = roles;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public SM_UserMessage setStatus(boolean status) {
        this.status = status;
        return this;
    }
}
