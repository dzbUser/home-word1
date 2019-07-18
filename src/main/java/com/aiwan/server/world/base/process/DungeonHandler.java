package com.aiwan.server.world.base.process;

import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.user.role.fight.pvpunit.MonsterUnit;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.MonsterGenerateUtil;
import com.aiwan.server.world.base.scene.DungeonScene;
import com.aiwan.server.world.scenes.command.ChangeMapCommand;
import com.aiwan.server.world.scenes.mapresource.GateBean;
import com.aiwan.server.world.scenes.model.Position;

import java.util.List;

/**
 * 副本处理器
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
public class DungeonHandler {

    /**
     * 副本
     */
    private DungeonScene dungeonScene;

    /**
     * 击杀怪物数
     */
    private int killMonsterNum;

    /**
     * 总击杀数
     */
    private int totalKillNum;

    /**
     * 关卡数
     */
    private int checkpointNum;

    /**
     * 关卡监听器
     */
    public void checkpointListener(int killNum) {
        /*
        0.发放关卡奖励
        1.判断是否是最后一个关卡
        2.若不是，则刷下个关卡的怪
        3.若是，则判断是否是不通关副本，若是则再次刷最后关卡的怪，若不是则结算
         */
        //获取关卡
        GateBean gateBean = dungeonScene.getResource().getGateBeanMap().get(checkpointNum);
        //判断怪物是否四万
        if (gateBean.getMonsterNum() == killNum) {
            killNum = 0;
        }
    }

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
            GetBean.getSceneExecutorService().submit(new ChangeMapCommand(role, 1));
        }
    }

    /**
     * 结算
     */
    public void settleMent() {
        /*
        1.发送奖励给所有队员
         */
        existDungeon();
    }


    /**
     * 生成怪物
     *
     * @param monsterId 怪物id
     * @param num       生成怪物数量
     */
    public void generateMonster(int monsterId, int num) {
        //获取属于该地图的怪物
        List<MonsterResource> monsterResources = GetBean.getMonsterManager().getMonsterList(dungeonScene.getMapId());
        if (monsterResources == null) {
            return;
        }
        //循环创建怪物
        for (MonsterResource monsterResource : monsterResources) {
            for (int i = 0; i < monsterResource.getNum(); i++) {
                //获取随机坐标
                while (true) {
                    Position position = MonsterGenerateUtil.generaterRandomPosition(dungeonScene.getResource().getWidth(), dungeonScene.getResource().getHeight());
                    //判断不是阻挡点
                    if (GetBean.getMapManager().allowMove(position.getX(), position.getY(), dungeonScene.getMapId())) {
                        MonsterUnit monster = MonsterUnit.valueOf(monsterResource, position);
                        dungeonScene.getBaseUnitMap().put(monster.getId(), monster);
                        break;
                    }
                }
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
}
