package com.aiwan.server.base.executor.account.impl;

import com.aiwan.server.base.executor.AbstractCommand;

/**
 * @author dengzebiao
 * 账号抽象command
 */
public abstract class AbstractAccountCommand extends AbstractCommand {
    private String accountId;

    public AbstractAccountCommand(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    @Override
    public Object getKey() {
        return accountId;
    }


    @Override
    public int modIndex(int poolSize) {
        return Math.abs(accountId.hashCode() % poolSize);
    }
}
