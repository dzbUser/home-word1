package com.aiwan.server.base.executor.account;

import com.aiwan.server.base.executor.account.impl.AbstractAccountCommand;

public interface IAccountExecutorService {

    /**
     * 传入runnable任务
     */
    void addTask(String account, Runnable runnable);

    /**
     * @param command 执行命令
     */
    void submit(AbstractAccountCommand command);
}
