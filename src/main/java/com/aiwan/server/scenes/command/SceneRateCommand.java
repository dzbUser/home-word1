package com.aiwan.server.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneRateCommand;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.scenes.model.SceneObject;
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


    /**
     * 刷怪间隔
     */
    private long brushPeriod;

    /**
     * 下次刷怪时间
     */
    private long nextBrushTime;

    public SceneRateCommand(String accountId, int mapId, long delay, long period, long brushMonsterTime, long brushPeriod) {
        super(accountId, mapId, delay, period);
        this.nextBrushTime = brushMonsterTime;
        this.brushPeriod = brushPeriod;
        setTaskName("场景循环命令");
    }

    @Override
    public void action() {
        SceneObject sceneObject = GetBean.getMapManager().getSceneObject(getKey());
        if (sceneObject == null) {
            logger.error("地图id：{}开启周期检查线程失败", getMapId());
            return;
        }
        long now = System.currentTimeMillis();
        //复活死亡怪物
        if (now >= nextBrushTime) {
            Map<Long, BaseUnit> baseUnitMap = sceneObject.getBaseUnitMap();
            for (BaseUnit baseUnit : baseUnitMap.values()) {
                if (baseUnit.isMonster() && baseUnit.isDeath()) {
                    baseUnit.setHp(baseUnit.getMaxHp());
                    baseUnit.setDeath(false);
                }
            }
            nextBrushTime = now + brushPeriod;
        }

        //buff处理
        for (BaseUnit baseUnit : sceneObject.getBaseUnitMap().values()) {
            baseUnit.buffProcessor(now);
        }
    }
}
