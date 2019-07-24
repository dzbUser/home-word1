package com.aiwan.server.world.base.handler.impl;

import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.reward.model.RewardBean;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import com.aiwan.server.reward.command.RandomRewardCommand;
import com.aiwan.server.world.base.handler.AbstractChapterDungeonHandler;
import com.aiwan.server.world.scenes.command.EnterMapCommand;
import com.aiwan.server.world.scenes.mapresource.GateBean;
import com.aiwan.server.world.scenes.mapresource.SettlementBean;

/**
 * 定时关卡副本处理器
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
public class ExperienceDungeonHandler extends AbstractChapterDungeonHandler {


    /**
     * 副本主体
     */
    private Role role;

    /**
     * 总击杀数
     */
    private int totalKillNum;

    /**
     * 标志是否所有奖励已发放
     */
    private boolean allReward = false;


    public ExperienceDungeonHandler(Role role) {
        this.role = role;
    }

    /**
     * 关卡监听器
     */
    @Override
    public void checkpointListener() {
        /*
        0.发放关卡奖励
        1.判断是否是最后一个关卡
        2.若不是，则刷下个关卡的怪
        3.若是，则判断是否是不通关副本，若是则再次刷最后关卡的怪，若不是则结算
         */
        //添加杀怪数
        setTotalKillNum(getTotalKillNum() + 1);
        setKillMonsterNum(getKillMonsterNum() + 1);
        //获取关卡
        GateBean gateBean = getDungeonScene().getResource().getGateBeanMap().get(getCheckpointNum());
        //判断怪物是否全部死亡
        if (gateBean.getMonsterNum() == getKillMonsterNum()) {
            //清楚所有怪物尸体
            clearAllMonster();
            setKillMonsterNum(0);
            //发放奖励
            if (!allReward) {
                gateReward(gateBean);
            }
            //判断是否有下一关卡的怪
            GateBean nextGateBean = getDungeonScene().getResource().getGateBeanMap().get(getCheckpointNum() + 1);
            if (nextGateBean != null) {
                //还有下一关
                setCheckpointNum(getCheckpointNum() + 1);
                generateMonster(nextGateBean.getMonsterId(), nextGateBean.getMonsterNum());
            } else {
                //没有下一关
                generateMonster(gateBean.getMonsterId(), gateBean.getMonsterNum());
                //标志所有奖励发放完毕
                allReward = true;
            }
        }
    }

    @Override
    public void gateReward(GateBean gateBean) {
        GetBean.getAccountExecutorService().submit(new RandomRewardCommand(role.getAccountId(), role.getId(), gateBean.getRewardBean()));
    }

    @Override
    public void settlementReward(SettlementBean settlementBean) {
        //取消循环处理器
        RewardBean rewardBean = RewardBean.valueOf(settlementBean.getDropBeanList(), settlementBean.getExperience() * totalKillNum);
        GetBean.getAccountExecutorService().submit(new RandomRewardCommand(role.getAccountId(), role.getId(), rewardBean));
    }

    @Override
    public void init() {
        /*
        初始化副本
        1.关卡是为1
        2.初始化怪物
         */
        this.totalKillNum = 0;
        super.init();
    }

    @Override
    public void enterDungeon(Role role, RoleUnit roleUnit) {
        GetBean.getScenesService().enterMap(role, getDungeonScene(), roleUnit);
    }

    @Override
    public void quitDungeon(Role role) {
        getDungeonScene().removeBaseUnit(role.getId());
        GetBean.getMapManager().sendMessageToUsers(getDungeonScene().getMapId(), getDungeonScene().getSceneId());
    }

    @Override
    public void releaseDungeon() {
        GetBean.getSceneExecutorService().submit(new EnterMapCommand(1, role, (RoleUnit) getDungeonScene().getBaseUnit(role.getId())));
        SessionManager.sendPromptMessage(role.getId(), PromptCode.RETUEN_CITY, "副本时间到");
        super.releaseDungeon();
    }


    /**
     * 结算
     */
    @Override
    public void settlement() {
        //发送奖励
        settlementReward(getDungeonScene().getResource().getSettlementBean());
        //释放资源
        releaseDungeon();
        //发送副本通关事假
        dungeonClearanceEvent(role, getDungeonScene().getMapId());
    }

    public int getTotalKillNum() {
        return totalKillNum;
    }

    public void setTotalKillNum(int totalKillNum) {
        this.totalKillNum = totalKillNum;
    }

}
