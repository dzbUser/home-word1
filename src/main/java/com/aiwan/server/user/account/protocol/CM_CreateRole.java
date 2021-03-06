package com.aiwan.server.user.account.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dzb
 * 角色创建协议类
 * */
@ProtocolAnnotation(protocol = Protocol.CREATEROLE)
public class CM_CreateRole implements Serializable {
    private int sex;
    private int job;
    private String accountId;

    /**
     * 角色名字
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public CM_CreateRole setSex(int sex) {
        this.sex = sex;
        return this;
    }

    public int getJob() {
        return job;
    }

    public CM_CreateRole setJob(int job) {
        this.job = job;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public CM_CreateRole setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }
}
