package com.aiwan.server.world.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneRateCommand;
import com.aiwan.server.user.role.fight.pvpunit.BaseUnit;
import com.aiwan.server.world.base.scene.AbstractScene;
import com.aiwan.server.world.base.scene.UniqueScene;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * 场景循环命令
 *
 * @author dengzebiao
 * @since 2019.7.9
 */
public class SceneRateCommand extends AbstractSceneRateCommand {

    Logger logger = LoggerFactory.getLogger(SceneRateCommand.class);


    public SceneRateCommand(String accountId, int mapId, long delay, long period) {
        super(accountId, mapId, delay, period);
        setTaskName("场景循环命令");
    }

    public SceneRateCommand(String accountId, int mapId, int sceneId, long delay, long period) {
        super(accountId, mapId, sceneId, delay, period);
        setTaskName("场景循环命令");
    }

    @Override
    public void action() {
        AbstractScene abstractScene = GetBean.getMapManager().getSceneObject(getMapId(), getSceneId());
        if (abstractScene == null) {
            logger.error("场景id：{}开启周期检查线程失败", getKey());
            return;
        }
        long now = System.currentTimeMillis();

        //buff处理
        for (BaseUnit baseUnit : abstractScene.getBaseUnitMap().values()) {
            baseUnit.buffProcessor(now);
        }
    }

    @Override
    public String getTaskName() {
        return "SceneRateCommand";
    }
}
