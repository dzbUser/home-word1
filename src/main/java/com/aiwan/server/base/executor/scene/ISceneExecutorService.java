package com.aiwan.server.base.executor.scene;

import com.aiwan.server.base.executor.ICommand;
import com.aiwan.server.base.executor.account.impl.AbstractAccountCommand;

/**
 * @author dengzebiao
 * 命令执行服务
 */
public interface ISceneExecutorService {

    /**
     * @param command 执行命令
     */
    void submit(ICommand command);
}
