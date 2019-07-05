package com.aiwan.server.base.executor.account;

import com.aiwan.server.base.executor.account.impl.AbstractAccountCommand;
import com.aiwan.server.base.executor.account.impl.AbstractAccountDelayCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dengzebiao
 * @since 2019.7.2
 * 用户线程分配
 */
@Component
public class AccountExecutorService implements IAccountExecutorService {

    @Autowired
    private AccountExecutor accountExecutor;

    @Override
    public void addTask(String account, Runnable runnable) {
        accountExecutor.addTask(account, runnable);
    }

    @Override
    public void submit(AbstractAccountCommand command) {
        if (command instanceof AbstractAccountDelayCommand) {
            //定时任务
            accountExecutor.schedule(command, ((AbstractAccountDelayCommand) command).getDelay());
        } else {
            accountExecutor.addTask(command);
        }
    }
}
