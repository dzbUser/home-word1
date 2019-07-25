package com.aiwan.server.world.base.handler.impl;

import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.team.model.TeamModel;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.LoggerUtil;
import com.aiwan.server.util.PromptCode;
import com.aiwan.server.reward.command.RandomRewardCommand;
import com.aiwan.server.world.base.handler.AbstractChapterDungeonHandler;
import com.aiwan.server.world.scenes.command.EnterMapCommand;
import com.aiwan.server.world.scenes.mapresource.GateBean;
import com.aiwan.server.world.scenes.mapresource.SettlementBean;

/**
 * 科赞岛副本处理器
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public class KozanIslandDungeonHandler extends AbstractChapterDungeonHandler {

    /**
     * 是否通关
     */
    private boolean isClearance = false;

    /**
     * 队伍
     */
    private TeamModel teamModel;

    public KozanIslandDungeonHandler(TeamModel teamModel) {
        this.teamModel = teamModel;
    }

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
    public void gateReward(GateBean gateBean) {
        for (Role role : teamModel.getTeamList()) {
            LoggerUtil.info("{}通关副本关卡{},发放奖励", role.getId(), gateBean.getGateNum());
            GetBean.getAccountExecutorService().submit(new RandomRewardCommand(role.getAccountId(), role.getId(), gateBean.getRewardBean()));
        }
    }

    @Override
    public void settlementReward(SettlementBean settlementBean) {
        for (Role role : teamModel.getTeamList()) {
            LoggerUtil.info("{}通关副本,发放奖励", role.getId());
            GetBean.getAccountExecutorService().submit(new RandomRewardCommand(role.getAccountId(), role.getId(), settlementBean.getRewardBean()));
        }
    }

    @Override
    public void enterDungeon(Role role, RoleUnit roleUnit) {
        GetBean.getScenesService().enterMap(role, getDungeonScene(), roleUnit);
    }

    @Override
    public void quitDungeon(Role role) {
        //退出队伍
        GetBean.getTeamService().leaveTeam(role.getId());
        //退出副本
        getDungeonScene().removeBaseUnit(role.getId());
        GetBean.getMapManager().sendMessageToUsers(getDungeonScene().getMapId(), getDungeonScene().getSceneId());
    }

    @Override
    public void settlement() {
        /*
        1.发送奖励给所有队员
         */

        //若已通关
        if (isClearance) {
            //发送奖励
            settlementReward(getDungeonScene().getResource().getSettlementBean());
            //触发副本通关事件
            for (Role role : teamModel.getTeamList()) {
                dungeonClearanceEvent(role, getDungeonScene().getMapId());
            }
        }
        releaseDungeon();
    }

    public void releaseDungeon() {
        for (Role role : teamModel.getTeamList()) {
            GetBean.getSceneExecutorService().submit(new EnterMapCommand(1, role, (RoleUnit) getDungeonScene().getBaseUnit(role.getId())));
        }
        //发送提示
        for (Role role : teamModel.getTeamList()) {
            SessionManager.sendPromptMessage(role.getId(), PromptCode.RETUEN_CITY, "");
        }
        super.releaseDungeon();
    }
}
