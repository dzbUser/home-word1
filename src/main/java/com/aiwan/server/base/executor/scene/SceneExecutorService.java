package com.aiwan.server.base.executor.scene;

import com.aiwan.server.base.executor.ICommand;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 场景执行任务
 *
 * @author dengzebiao
 */
public class SceneExecutorService implements ISceneExecutorService {
    @Autowired
    private SceneExecutor sceneExecutor;

    @Override
    public void submit(ICommand command) {
        sceneExecutor.addTask(command);
    }
}
