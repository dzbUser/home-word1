package com.aiwan.server.base.executor.scene;

import com.aiwan.server.base.executor.ICommand;
import com.aiwan.server.base.executor.scene.impl.AbstractSceneDelayCommand;
import com.aiwan.server.base.executor.scene.impl.AbstractSceneRateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 场景执行任务
 *
 * @author dengzebiao
 */
@Component
public class SceneExecutorService implements ISceneExecutorService {

    @Autowired
    private SceneExecutor sceneExecutor;

    @Override
    public void submit(ICommand command) {
        if (command instanceof AbstractSceneRateCommand) {
            AbstractSceneRateCommand sceneRateCommand = (AbstractSceneRateCommand) command;
            sceneExecutor.schedule(sceneRateCommand, sceneRateCommand.getDelay(), sceneRateCommand.getPeriod());
        } else if (command instanceof AbstractSceneDelayCommand) {
            AbstractSceneDelayCommand sceneDelayCommand = (AbstractSceneDelayCommand) command;
            sceneExecutor.schedule(sceneDelayCommand, sceneDelayCommand.getDelay());
        } else {
            sceneExecutor.addTask(command);
        }
    }
}
