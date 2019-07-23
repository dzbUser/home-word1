package com.aiwan.server.world.base.handler;

import com.aiwan.server.base.event.event.impl.DungeonClearanceEvent;
import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.user.role.fight.pvpunit.BaseUnit;
import com.aiwan.server.user.role.fight.pvpunit.MonsterUnit;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.MonsterGenerateUtil;
import com.aiwan.server.world.base.scene.DungeonScene;
import com.aiwan.server.world.dungeon.command.DungeonOverCommand;
import com.aiwan.server.reward.command.RewardCommand;
import com.aiwan.server.world.scenes.command.ChangeMapCommand;
import com.aiwan.server.world.scenes.command.EnterMapCommand;
import com.aiwan.server.world.scenes.mapresource.GateBean;
import com.aiwan.server.world.scenes.mapresource.SettlementBean;
import com.aiwan.server.world.scenes.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * 副本抽象处理器
 *
 * @author dengzebiao
 * @since 2019.7.19
 */
public abstract class AbstractDungeonHandler {

    Logger logger = LoggerFactory.getLogger(AbstractDungeonHandler.class);

    /**
     * 副本
     */
    private DungeonScene dungeonScene;

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
    public void gateReward(GateBean gateBean) {
        for (Role role : getDungeonScene().getTeamModel().getTeamList()) {
            GetBean.getAccountExecutorService().submit(new RewardCommand(role.getAccountId(), role.getId(), gateBean.getRewardBean()));
        }
    }

    /**
     * 发放结算奖励
     */
    public abstract void settlementReward(SettlementBean settlementBean);

    public void init() {
        /*
        初始化副本
        1.关卡是为1
        2.初始化怪物
         */
        this.killMonsterNum = 0;
        this.checkpointNum = 1;
        GateBean gateBean = dungeonScene.getResource().getGateBeanMap().get(1);
        //初始化怪物
        generateMonster(gateBean.getMonsterId(), gateBean.getMonsterNum());
        //添加定时器
        DungeonOverCommand dungeonOverCommand = new DungeonOverCommand(getDungeonScene().getResource().getDuration(), null, dungeonScene.getMapId(), dungeonScene.getSceneId(), getDungeonScene());
        getDungeonScene().getCommandMap().put(DungeonOverCommand.class, dungeonOverCommand);
        //启动定时器
        GetBean.getSceneExecutorService().submit(dungeonOverCommand);
        enterDungeon();
    }

    /**
     * 创建副本后玩家进入副本（初始化之后调用）
     */
    public void enterDungeon() {
        /*
        1.队伍的成员循环进入副本
         */
        for (Role role : dungeonScene.getTeamModel().getTeamList()) {
            GetBean.getSceneExecutorService().submit(new ChangeMapCommand(role, dungeonScene.getMapId(), dungeonScene.getSceneId()));
        }
    }

    /**
     * 结算后调用
     */
    public void existDungeon() {
        /*
        1.队伍的队员回到初始点
        2.删除地图资源
         */
        for (Role role : dungeonScene.getTeamModel().getTeamList()) {
            GetBean.getSceneExecutorService().submit(new EnterMapCommand(1, role, (RoleUnit) dungeonScene.getBaseUnit(role.getId())));
        }
    }

    /**
     * 结算
     */
    public abstract void settlement();


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
            logger.error("{}生成怪物{}错误，未找到怪物静态资源", dungeonScene.getSceneId(), monsterId);
            return;
        }
        for (int i = 0; i < num; i++) {
            //获取随机坐标
            while (true) {
                Position position = MonsterGenerateUtil.generaterRandomPosition(dungeonScene.getResource().getWidth(), dungeonScene.getResource().getHeight());
                //判断不是阻挡点
                if (GetBean.getMapManager().allowMove(position.getX(), position.getY(), dungeonScene.getMapId())) {
                    MonsterUnit monster = MonsterUnit.valueOf(monsterResource, position, getDungeonScene().getKey(), getDungeonScene().getMapId());
                    dungeonScene.getBaseUnitMap().put(monster.getId(), monster);
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

    public DungeonScene getDungeonScene() {
        return dungeonScene;
    }

    public void setDungeonScene(DungeonScene dungeonScene) {
        this.dungeonScene = dungeonScene;
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

