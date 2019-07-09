package com.aiwan.server.user.role.fight.service;

import com.aiwan.server.scenes.fight.model.pvpunit.BaseUnit;
import com.aiwan.server.scenes.fight.model.pvpunit.FighterRole;
import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.scenes.model.SceneObject;
import com.aiwan.server.user.role.fight.command.DoUserSkillCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.skill.model.AbstractSkill;
import com.aiwan.server.user.role.skill.model.SkillModel;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
    public void doUserSkill(Long activeRid, Long passiveId, AbstractSkill skill, int mapId, int castingType) {

        /*
         * 获取战斗角色，
         * 判断是否可以施法技能
         * 获取施法目标
         * 实用技能
         * */
        //获取场景对象
        SceneObject sceneObject = GetBean.getMapManager().getSceneObject(mapId);
        //获取施法单位
        FighterRole activeRole = sceneObject.getFighterRole(activeRid);
        if (activeRole == null) {
            //没有改施法单位
            logger.error("角色{}施法错误，找不到施法单位", activeRid);
            return;
        }

        //获取目标施法单位
        BaseUnit passiveUnit;
        if (castingType == ATTACKROLE) {
            passiveUnit = sceneObject.getFighterRole(passiveId);
        } else {
            passiveUnit = sceneObject.getMonster(passiveId);
        }
        if (passiveUnit == null) {
            //没有目标施法单位
            logger.error("角色{}施法错误，找不到施法单位", activeRid);
            return;
        }

        //是否在攻击范围内
        if (!isDistanceSatisfy(activeRole, passiveUnit, skill.getSkillLevelResouece().getDistance())) {
            logger.error("角色{}施法错误，施法距离不够", activeRid);
            return;
        }

        //玩家是否具有释放该技能的条件
        if (!activeRole.isCanUseSkill(skill)) {
            logger.error("角色{}施法错误，不具备施法添加", activeRid);
            return;
        }
        //释放技能
        skill.doUserSkill(activeRole, passiveUnit);
    }

    @Override
    public void userSkill(Long activeRid, Long passiveId, int position, int aimType) {
        Role role = GetBean.getRoleManager().load(activeRid);
        // TODO: 2019/7/9 判断role
        //获取技能
        SkillModel skillModel = GetBean.getSkillManager().load(activeRid);
        //获取对应的技能id
        Integer skillId = skillModel.getSkillIdInPosition(position);
        if (skillId == null) {
            logger.error("角色{}施法错误，位置{}没有技能", activeRid, position);
            return;
        }
        //获取技能
        AbstractSkill skill = skillModel.getSkill(skillId);
        //移交场景command
        GetBean.getSceneExecutorService().submit(new DoUserSkillCommand(null, role.getMap(), activeRid, passiveId, skill, aimType));
    }

    private boolean isDistanceSatisfy(BaseUnit activeRole, BaseUnit passiveRole, int distance) {
        Position activePosition = activeRole.getPosition();
        Position passivePosition = passiveRole.getPosition();
        double juli = Math.sqrt(Math.abs((activePosition.getX() - passivePosition.getX()) * (activePosition.getX() - passivePosition.getX()) + (activePosition.getY() - passivePosition.getY()) * (activePosition.getY() - passivePosition.getY())));
        if (distance > juli) {
            return false;
        }
        return true;
    }
}
