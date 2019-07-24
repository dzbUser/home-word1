package com.aiwan.server.world.base.handler;

import com.aiwan.server.base.event.event.impl.DungeonClearanceEvent;
import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.user.role.fight.pvpunit.BaseUnit;
import com.aiwan.server.user.role.fight.pvpunit.MonsterUnit;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.MonsterGenerateUtil;
import com.aiwan.server.world.scenes.mapresource.GateBean;
import com.aiwan.server.world.scenes.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * 有关卡副本抽象处理器
 *
 * @author dengzebiao
 * @since 2019.7.19
 */
public abstract class AbstractChapterDungeonHandler extends AbstractDungeonHandler {

    Logger logger = LoggerFactory.getLogger(AbstractChapterDungeonHandler.class);


    /**
     * 本关卡击杀怪物数
     */
    private int killMonsterNum;


    /**
     * 关卡数
     */
    private int checkpointNum;

    /**
     * 关卡监听器
     */
    public abstract void checkpointListener();

    /**
     * 发放关卡奖励
     */
    public abstract void gateReward(GateBean gateBean);

    public void init() {
        /*
        初始化副本
        1.关卡是为1
        2.初始化怪物
         */
        this.killMonsterNum = 0;
        this.checkpointNum = 1;
        GateBean gateBean = getDungeonScene().getResource().getGateBeanMap().get(1);
        //初始化怪物
        generateMonster(gateBean.getMonsterId(), gateBean.getMonsterNum());
        super.init();
    }


    /**
     * 结算后调用
     */
    public void releaseDungeon() {
        super.releaseDungeon();
    }


    /**
     * 生成怪物
     *
     * @param monsterId 怪物id
     * @param num       生成怪物数量
     */
    public void generateMonster(int monsterId, int num) {
        //获取属于该地图的怪物
        MonsterResource monsterResource = GetBean.getMonsterManager().getResourceById(monsterId);
        if (monsterResource == null) {
            logger.error("{}生成怪物{}错误，未找到怪物静态资源", getDungeonScene().getSceneId(), monsterId);
            return;
        }
        for (int i = 0; i < num; i++) {
            //获取随机坐标
            while (true) {
                Position position = MonsterGenerateUtil.generaterRandomPosition(getDungeonScene().getResource().getWidth(), getDungeonScene().getResource().getHeight());
                //判断不是阻挡点
                if (GetBean.getMapManager().allowMove(position.getX(), position.getY(), getDungeonScene().getMapId())) {
                    MonsterUnit monster = MonsterUnit.valueOf(monsterResource, position, getDungeonScene().getKey(), getDungeonScene().getMapId());
                    getDungeonScene().getBaseUnitMap().put(monster.getId(), monster);
                    break;
                }
            }
        }
    }

    /**
     * 清楚所有怪物尸体
     */
    public void clearAllMonster() {
        Map<Long, BaseUnit> baseUnitMap = getDungeonScene().getBaseUnitMap();
        for (Iterator<Map.Entry<Long, BaseUnit>> it = baseUnitMap.entrySet().iterator(); it.hasNext(); ) {
            BaseUnit baseUnit = it.next().getValue();
            if (baseUnit.isMonster()) {
                it.remove();
            }
        }
    }

    public int getKillMonsterNum() {
        return killMonsterNum;
    }

    public void setKillMonsterNum(int killMonsterNum) {
        this.killMonsterNum = killMonsterNum;
    }

    public int getCheckpointNum() {
        return checkpointNum;
    }

    public void setCheckpointNum(int checkpointNum) {
        this.checkpointNum = checkpointNum;
    }

    /**
     * 发送通关事件
     *
     * @param role  角色
     * @param mapId 通关地图id
     */
    public void dungeonClearanceEvent(Role role, int mapId) {
        DungeonClearanceEvent dungeonClearanceEvent = DungeonClearanceEvent.valueOf(role, mapId);
        GetBean.getEventBusManager().synSubmit(dungeonClearanceEvent);
    }
}

