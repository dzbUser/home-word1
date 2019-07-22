package com.aiwan.server.world.base.handler;

import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import com.aiwan.server.world.dungeon.command.RewardCommand;
import com.aiwan.server.world.scenes.mapresource.GateBean;
import com.aiwan.server.world.scenes.mapresource.SettlementBean;

/**
 * 通关副本处理器
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public class ClearanceDungeonHandler extends AbstractDungeonHandler {

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
                settlement();
            }
        }
    }

    @Override
    public void settlementReward(SettlementBean settlementBean) {
        for (Role role : getDungeonScene().getTeamModel().getTeamList()) {
            GetBean.getAccountExecutorService().submit(new RewardCommand(role.getAccountId(), role.getId(), settlementBean.getDropBeanList(), settlementBean.getExperience()));
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
        //发送奖励
        settlementReward(getDungeonScene().getResource().getSettlementBean());
        //发送提示
        for (Role role : getDungeonScene().getTeamModel().getTeamList()) {
            SessionManager.sendPromptMessage(role.getId(), PromptCode.RETUEN_CITY, "");
        }
    }
}
