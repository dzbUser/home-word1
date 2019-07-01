package com.aiwan.server.user.role.skill.model;

import com.aiwan.server.user.role.skill.entity.SkillEntity;
import com.aiwan.server.util.GetBean;

import java.util.Collections;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.7.1
 * 技能模型
 */
public class SkillModel {

    /**
     * 实体类
     */
    private SkillEntity skillEntity;

    /**
     * 获取最大技能栏数
     */
    public int getMaxSkillBarNum() {
        return skillEntity.getMaxSkillBarNum();
    }

    public void setSkillEntity(SkillEntity skillEntity) {
        this.skillEntity = skillEntity;
    }

    public SkillEntity getSkillEntity() {
        return skillEntity;
    }

    /**
     * 根据技能id获取技能
     */
    public Skill getSkillBySkillId(int skillId) {
        return skillEntity.getSkillInfo().getSkillMap().get(skillId);
    }


    /**
     * 学习技能
     */
    public void putSkillBySkillId(int skillId, int skillTypeId) {
        //创建普通技能
        Skill skill = SkillType.getSkillById(skillTypeId);
        skill.setSkillId(skillId);
        skill.setSkillLevel(1);
        skillEntity.getSkillInfo().getSkillMap().put(skillId, skill);
    }

    /**
     * 返回所学技能
     */
    public Map<Integer, Skill> getLearnedSkill() {
        return skillEntity.getSkillInfo().getSkillMap();
    }

    /**
     * 技能升级
     */
    public void upgrade(int skillId) {
        Skill skill = skillEntity.getSkillInfo().getSkillMap().get(skillId);
        skill.setSkillLevel(skill.getSkillLevel() + 1);
    }

    /**
     * 移动技能到技能栏的postion位置
     */
    public void moveSkillToPosition(Skill skill, int position) {
        Skill[] skills = skillEntity.getSkillInfo().getSkills();
        for (int i = 0; i < skills.length; i++) {
            if (skills[i] != null && skills[i].getSkillId() == skill.getSkillId()) {
                skills[i] = null;
            }
        }
        skills[position] = skill;
    }

    /**
     * 获取技能栏
     */
    public Skill[] getSkillBar() {
        return skillEntity.getSkillInfo().getSkills();
    }
}
