package com.aiwan.server.world.base.handler;

import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import com.aiwan.server.reward.command.RewardCommand;
import com.aiwan.server.world.scenes.mapresource.GateBean;
import com.aiwan.server.world.scenes.mapresource.SettlementBean;

/**
 * 通关副本处理器
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public class ClearanceDungeonHandler extends AbstractDungeonHandler {

    /**
     * 是否通关
     */
    private boolean isClearance = false;

    @Override
    public void checkpointListener() {
        setKillMonsterNum(getKillMonsterNum() + 1);
        //获取关卡
        GateBean gateBean = getDungeonScene().getResource().getGateBeanMap().get(getCheckpointNum());
        //判断怪物是否全部死亡
        if (gateBean.getMonsterNum() == getKillMonsterNum()) {
            //清楚所有怪物尸体
            clearAllMonster();
            setKillMonsterNum(0);
            //发放关卡奖励
            gateReward(gateBean);
            //判断是否有下一关卡的怪
            GateBean nextGateBean = getDungeonScene().getResource().getGateBeanMap().get(getCheckpointNum() + 1);
            if (nextGateBean != null) {
                //还有下一关
                setCheckpointNum(getCheckpointNum() + 1);
                generateMonster(nextGateBean.getMonsterId(), nextGateBean.getMonsterNum());
            } else {
                //通关，结算
                isClearance = true;
                settlement();
            }
        }
    }

    @Override
    public void settlementReward(SettlementBean settlementBean) {
        for (Role role : getDungeonScene().getTeamModel().getTeamList()) {
            GetBean.getAccountExecutorService().submit(new RewardCommand(role.getAccountId(), role.getId(), settlementBean.getRewardBean()));
        }
    }

    @Override
    public void settlement() {
        /*
        1.发送奖励给所有队员
         */
        existDungeon();
        //删除副本
        GetBean.getMapManager().removeSceObject(getDungeonScene().getMapId(), getDungeonScene().getSceneId());
        //若已通关
        if (isClearance) {
            //发送奖励
            settlementReward(getDungeonScene().getResource().getSettlementBean());
            //触发副本通关事件
            //触发副本通关事件
            for (Role role : getDungeonScene().getTeamModel().getTeamList()) {
                dungeonClearanceEvent(role, getDungeonScene().getMapId());
            }
        }
        //发送副本通关事件

        //发送提示
        for (Role role : getDungeonScene().getTeamModel().getTeamList()) {
            SessionManager.sendPromptMessage(role.getId(), PromptCode.RETUEN_CITY, "");
        }
    }
}
