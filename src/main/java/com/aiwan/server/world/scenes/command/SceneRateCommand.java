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
        AbstractScene abstractScene = GetBean.getMapManager().getSceneObject(getMapId(), getSceneId());
        if (abstractScene == null) {
            logger.error("场景id：{}开启周期检查线程失败", getKey());
            return;
        }
        long now = System.currentTimeMillis();
        //复活死亡怪物
        if (now >= nextBrushTime) {
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
            nextBrushTime = now + brushPeriod;
            if (flag) {
                GetBean.getMapManager().sendMessageToUsers(getMapId(), getSceneId());
            }
        }

        //buff处理
        for (BaseUnit baseUnit : abstractScene.getBaseUnitMap().values()) {
            baseUnit.buffProcessor(now);
        }
    }
}
