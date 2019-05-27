package com.aiwan.user.protocol;

import com.aiwan.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.util.Protocol;

@ProtocolAnnotation(protocol = Protocol.CREATEROLE)
public class CM_CreateRole {
    private int sex;
    private int job;
    private String accountId;

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
