package com.aiwan.server.user.role.fight.service;

import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.fight.pvpUnit.FighterRole;
import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.scenes.model.SceneObject;
import com.aiwan.server.user.role.fight.command.DoUserSkillCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.skill.model.Skill;
import com.aiwan.server.user.role.skill.model.SkillModel;
import com.aiwan.server.util.GetBean;
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
            return;
        }

        //获取目标施法单位
        List<BaseUnit> passiveList = new ArrayList<>();
        BaseUnit passiveUnit = sceneObject.getBaseUnit(passiveId);
        if (passiveUnit == null || passiveUnit.isDeath()) {
            //没有目标施法单位
            logger.error("角色{}施法错误，找不到施法目标或者施法目标已死亡", activeRid);
            return;
        }
        //是否在攻击范围内
        if (!isDistanceSatisfy(activeRole, passiveUnit, skill.getSkillLevelResource().getDistance())) {
            logger.error("角色{}施法错误，施法距离不够", activeRid);
            return;
        }
        //玩家是否具有释放该技能的条件
        if (!activeRole.isCanUseSkill(skill)) {
            logger.error("角色{}施法错误，不具备施法添加", activeRid);
            return;
        }
        passiveList.add(passiveUnit);
        if (skill.getResource().getIsGroup() == 1) {
            int num = skill.getSkillLevelResource().getNum() - 1;
            sceneObject.findAroundUnit(activeRole, passiveList, passiveUnit, skill.getSkillLevelResource().getDistance(), num);
        }
        //释放技能
        skill.doUserSkill(activeRole, passiveList);
    }

    @Override
    public void userSkill(Long activeRid, Long passiveId, int position) {
        Role role = GetBean.getRoleManager().load(activeRid);
        if (role == null) {
            logger.error("角色id{}发送错误报", activeRid);
            return;
        }

        //获取技能
        SkillModel skillModel = GetBean.getSkillManager().load(activeRid);
        //超出技能
        if (position < 0 || position >= skillModel.getSkillBar().length) {
            logger.error("角色{}施法错误，位置{}超出技能槽范围", activeRid, position);
            return;
        }
        //获取对应的技能id
        Integer skillId = skillModel.getSkillIdInPosition(position);
        if (skillId == null) {
            logger.error("角色{}施法错误，位置{}没有技能", activeRid, position);
            return;
        }
        //获取技能
        Skill skill = skillModel.getSkill(skillId);
        //移交场景command
        GetBean.getSceneExecutorService().submit(new DoUserSkillCommand(null, role.getMap(), activeRid, passiveId, skill));
    }

    public boolean isDistanceSatisfy(BaseUnit activeRole, BaseUnit passiveRole, int distance) {
        Position activePosition = activeRole.getPosition();
        Position passivePosition = passiveRole.getPosition();
        double juli = Math.sqrt(Math.abs((activePosition.getX() - passivePosition.getX()) * (activePosition.getX() - passivePosition.getX()) + (activePosition.getY() - passivePosition.getY()) * (activePosition.getY() - passivePosition.getY())));
        if (distance < juli) {
            return false;
        }
        return true;
    }
}
