package com.aiwan.server.user.role.skill.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.skill.model.Skill;
import com.aiwan.server.user.role.skill.model.SkillModel;
import com.aiwan.server.user.role.skill.model.SkillType;
import com.aiwan.server.user.role.skill.protocol.SM_ViewLearnedSkill;
import com.aiwan.server.user.role.skill.protocol.element.SkillElement;
import com.aiwan.server.user.role.skill.resource.SkillLevelResource;
import com.aiwan.server.user.role.skill.resource.SkillResource;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 技能业务实现类
 *
 * @author dengzebiao
 * @since 2019.7.1
 */
@Service
public class SkillServiceImpl implements SkillService {

    private static Logger logger = LoggerFactory.getLogger(SkillServiceImpl.class);

    @Autowired
    private SkillManager skillManager;

    @Override
    public void learnSkill(Long rId, int skillId, int skillTypeId, Session session) {
        /*
         * 1.是否有该技能
         * 2.角色等级是否达到要求
         * 3.是否以学过该技能
         * 4.技能类型
         * */
        SkillResource skillResource = skillManager.getSkillResourceBySkillId(skillId);
        if (skillResource == null) {
            //没有该技能
            session.sendPromptMessage(PromptCode.NOSKILL, "");
            return;
        }

        //获取等级资源
        SkillLevelResource skillLevelResource = skillManager.getSkillLevelReById(skillId + "_1");
        //获取人物
        Role role = GetBean.getRoleManager().load(rId);
        if (skillLevelResource.getRoleLevelDemand() > role.getLevel()) {
            //角色等级未达到要求
            session.sendPromptMessage(PromptCode.NOTREACHEDLEVELDEMAND, "");
            return;
        }

        SkillModel skillModel = skillManager.load(rId);
        Skill skill = skillModel.getSkillBySkillId(skillId);
        if (skill != null) {
            //技能已经学过
            session.sendPromptMessage(PromptCode.HAVALEARN, "");
            return;
        }

        if (SkillType.getSkillById(skillTypeId) == null) {
            logger.error("角色id{}，学习技能id为{}时，输入的技能类型错误", rId, skillId);
            return;
        }

        //学习该技能
        skillModel.putSkillBySkillId(skillId, skillTypeId);
        skillManager.save(skillModel);
        session.sendPromptMessage(PromptCode.LEARNSKILLSUCCESS, skillResource.getSkillName());
    }

    @Override
    public void viewLearnedSkill(Long rId, Session session) {
        /*
         * 1.获取背包
         * 2.把背包存入协议类返回
         * */

        SkillModel skillModel = skillManager.load(rId);
        //获取不可修改的map
        Map<Integer, Skill> map = Collections.unmodifiableMap(skillModel.getLearnedSkill());
        List<SkillElement> list = new ArrayList<SkillElement>();
        for (Skill skill : map.values()) {
            SkillElement skillElement = SkillElement.valueOf(skill.getSkillId(), skill.getSkillLevel());
            list.add(skillElement);
        }
        //创建返回协议类
        SM_ViewLearnedSkill sm_viewLearnedSkill = SM_ViewLearnedSkill.valueOf(list);
        session.messageSend(SMToDecodeData.shift(StatusCode.VIEWLEARNEDSKILL, sm_viewLearnedSkill));
        return;
    }

    @Override
    public void upgradeSkill(Long rId, int skillId, Session session) {
        /*
         * 1.查看是否已学习该技能
         * 2.是否达到最高等级
         * 3.查看经验和升级等级是否达到
         * 4.升级技能
         * */
        SkillModel skillModel = skillManager.load(rId);
        Skill skill = skillModel.getSkillBySkillId(skillId);
        if (skill == null) {
            //还未学习该技能
            session.sendPromptMessage(PromptCode.NOTLEARNSKILL, "");
            return;
        }

        //获取静态资源
        SkillResource skillResource = skillManager.getSkillResourceBySkillId(skillId);
        if (skill.getSkillLevel() == skillResource.getMaxLevel()) {
            //达到最高等级
            session.sendPromptMessage(PromptCode.REACHMAXLEVEL, "");
            return;
        }


        //获取角色信息
        Role role = GetBean.getRoleManager().load(rId);
        //获取技能等级静态资源
        SkillLevelResource skillLevelResource = skillManager.getSkillLevelReById(skillId + "_" + (skill.getSkillLevel() + 1));
        if (role.getLevel() < skillLevelResource.getRoleLevelDemand() || role.getExperience() < skillLevelResource.getExperienceDemand()) {
            session.sendPromptMessage(PromptCode.NOTREARCHDEMAND, "");
            return;
        }
        //去除经验
        role.setExperience(role.getExperience() - skillLevelResource.getExperienceDemand());
        GetBean.getRoleManager().save(role);
        //技能升级
        skillModel.upgrade(skillId);
        skillManager.save(skillModel);
        session.sendPromptMessage(PromptCode.UPGRADESKILLSUCE, "");
    }

    @Override
    public void moveSkillToPosition(Long rId, int skillId, int position, Session session) {
        /*
         * 1.位置是否在越界
         * 2.技能是否已学
         * 3.移动位置
         * */
        SkillModel skillModel = skillManager.load(rId);
        if (position < 0 || position >= skillModel.getMaxSkillBarNum()) {
            //位置越界
            logger.info("角色{}移动技能到{}越界", rId, position);
            session.sendPromptMessage(PromptCode.INREGUARPOSITION, "");
            return;
        }

        //获取技能
        Skill skill = skillModel.getSkillBySkillId(skillId);
        if (skill == null) {
            session.sendPromptMessage(PromptCode.NOTLEARNSKILL, "");
            return;
        }

        skillModel.moveSkillToPosition(skill, position);
        skillManager.save(skillModel);
        session.sendPromptMessage(PromptCode.MOVESKILLSUCCESS, "");
    }
}
