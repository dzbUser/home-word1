package com.aiwan.server.world.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneRateCommand;
import com.aiwan.server.user.role.fight.pvpunit.BaseUnit;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.world.base.scene.AbstractScene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 怪物循环检查复活命令
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class MonsterRateReviveCommand extends AbstractSceneRateCommand {
    Logger logger = LoggerFactory.getLogger(MonsterRateReviveCommand.class);

    public MonsterRateReviveCommand(String accountId, int mapId, long delay, long period) {
        super(accountId, mapId, delay, period);
    }

    @Override
    public void action() {
        AbstractScene abstractScene = GetBean.getMapManager().getSceneObject(getMapId(), getSceneId());
        if (abstractScene == null) {
            logger.error("场景id：{}开启周期检查线程失败", getKey());
            return;
        }
        //复活死亡怪物
        //用以标志是否有怪物复活
        boolean flag = false;
        Map<Long, BaseUnit> baseUnitMap = abstractScene.getBaseUnitMap();
        for (BaseUnit baseUnit : baseUnitMap.values()) {
            if (baseUnit.isMonster() && baseUnit.isDeath()) {
                baseUnit.setHp(baseUnit.getMaxHp());
                baseUnit.setDeath(false);
                flag = true;
            }
        }
        if (flag) {
            //有怪物复活
            GetBean.getMapManager().sendMessageToUsers(getMapId(), getSceneId());
        }
    }

    /**
     * 获取任务名
     */
    @Override
    public String getTaskName() {
        return "MonsterRateReviveCommand";
    }
}
