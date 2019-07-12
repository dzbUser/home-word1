package com.aiwan.server.user.role.fight.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.fight.pvpUnit.FighterRole;
import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.scenes.model.SceneObject;
import com.aiwan.server.user.role.fight.command.DoUserSkillCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.skill.model.Skill;
import com.aiwan.server.user.role.skill.model.SkillModel;
import com.aiwan.server.util.FightUtil;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 战斗业务实现类
 *
 * @author dengzebiao
 * @since 2019.7.9
 */
@Service
public class FightService implements IFightService {

    /**
     * 攻击角色
     */
    private static int ATTACKROLE = 1;
    /**
     * 攻击目标
     */
    private static int ATTACKMONSTER = 2;

    Logger logger = LoggerFactory.getLogger(FightService.class);

    @Override
    public void doUserSkill(Long activeRid, Long passiveId, Skill skill, int mapId) {
        /*
         * 获取战斗角色，
         * 判断是否可以施法技能
         * 获取施法目标
         * 实用技能
         * */
        //获取场景对象
        SceneObject sceneObject = GetBean.getMapManager().getSceneObject(mapId);
        //获取施法单位
        FighterRole activeRole = (FighterRole) sceneObject.getBaseUnit(activeRid);

        if (activeRole == null) {
            //没有改施法单位
            logger.error("角色{}施法错误，找不到施法单位", activeRid);
            SessionManager.sendPromptMessage(activeRid, PromptCode.NOFIND_MAGICUNIT, "");
            return;
        }

        //获取目标施法单位
        List<BaseUnit> passiveList = new ArrayList<>();
        BaseUnit passiveUnit = sceneObject.getBaseUnit(passiveId);
        if (passiveUnit == null || passiveUnit.isDeath()) {
            //没有目标施法单位
            logger.error("角色{}施法错误，找不到施法目标或者施法目标已死亡", activeRid);
            SessionManager.sendPromptMessage(activeRid, PromptCode.NOFIND_MAGICAIM, "");
            return;
        }
        //是否在攻击范围内
        if (!isDistanceSatisfy(activeRole, passiveUnit, skill.getSkillLevelResource().getDistance())) {
            logger.error("角色{}施法错误，超出施法范围", activeRid);
            SessionManager.sendPromptMessage(activeRid, PromptCode.EXCEED_MAGICRANGE, "");
            return;
        }
        //玩家是否具有释放该技能的条件
        if (!activeRole.isCanUseSkill(skill)) {
            logger.error("角色{}施法错误，不具备施法条件", activeRid);
            SessionManager.sendPromptMessage(activeRid, PromptCode.BAN_USE_SKILL, "");
            return;
        }
        passiveList.add(passiveUnit);
        if (skill.getResource().getIsGroup() == 1) {
            int num = skill.getSkillLevelResource().getNum() - 1;
            sceneObject.findAroundUnit(activeRole, passiveList, passiveUnit, skill.getSkillLevelResource().getDistance(), num);
        }
        //释放技能
        skill.doUserSkill(activeRole, passiveList);
        SessionManager.sendPromptMessage(activeRid, PromptCode.USE_SKILL_SUCCESS, "");
        logger.info("角色{}施法成功", activeRid);
        //怪物反击
        monsterCounterattack(activeRole, passiveList);
    }

    @Override
    public void userSkill(Long activeRid, Long passiveId, int position) {
        Role role = GetBean.getRoleManager().load(activeRid);

        if (role == null) {
            logger.error("角色id{}发送错误报", activeRid);
            return;
        }
        Session session = SessionManager.getSessionByAccountId(role.getAccountId());
        //获取技能
        SkillModel skillModel = GetBean.getSkillManager().load(activeRid);
        //超出技能
        if (position < 0 || position >= skillModel.getSkillBar().length) {
            logger.error("角色{}施法错误，位置{}超出技能槽范围", activeRid, position);
            session.sendPromptMessage(PromptCode.EXCEED_SKILLBAR, "");
            return;
        }
        //获取对应的技能id
        Integer skillId = skillModel.getSkillIdInPosition(position);
        if (skillId == null) {
            logger.error("角色{}施法错误，位置{}没有技能", activeRid, position);
            session.sendPromptMessage(PromptCode.NOSKILL_INSKILLBAR, "");
            return;
        }
        //获取技能
        Skill skill = skillModel.getSkill(skillId);
        //移交场景command
        GetBean.getSceneExecutorService().submit(new DoUserSkillCommand(null, role.getMap(), activeRid, passiveId, skill));
    }

    @Override
    public boolean isDistanceSatisfy(BaseUnit activeRole, BaseUnit passiveRole, int distance) {
        Position activePosition = activeRole.getPosition();
        Position passivePosition = passiveRole.getPosition();
        double juli = Math.sqrt(Math.abs((activePosition.getX() - passivePosition.getX()) * (activePosition.getX() - passivePosition.getX()) + (activePosition.getY() - passivePosition.getY()) * (activePosition.getY() - passivePosition.getY())));
        if (distance < juli) {
            return false;
        }
        return true;
    }

    /**
     * 怪物反击
     *
     * @param active      施法单位
     * @param passiveList 施法目标列表
     */
    private void monsterCounterattack(BaseUnit active, List<BaseUnit> passiveList) {
        for (BaseUnit baseUnit : passiveList) {
            if (baseUnit.isMonster() && !active.isDeath()) {
                long hurt = FightUtil.calculateFinalHurt(baseUnit.getFinalAttribute(), active.getFinalAttribute(), 10000);
                hurt = Math.max(1, hurt);
                active.deduceHP(baseUnit.getId(), hurt);
                if (active.isDeath()) {
                    break;
                }
            }
        }
    }
}
