package com.aiwan.server.user.role.skill.model;

import com.aiwan.server.user.role.skill.entity.SkillEntity;
import com.aiwan.server.user.role.skill.entity.SkillInfo;
import com.aiwan.server.user.role.skill.entity.SkillElement;
import com.aiwan.server.user.role.skill.resource.SkillResource;
import com.aiwan.server.util.GetBean;

import java.util.Map;

/**
 * 技能模型
 *
 * @author dengzebiao
 * @since 2019.7.1
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
    public SkillElement getSkillBySkillId(int skillId) {
        return skillEntity.getSkillInfo().getSkillMessageMap().get(skillId);
    }


    /**
     * 学习技能
     */
    public void learnSkillBySkillId(int skillId) {
        //创建普通技能
        skillEntity.getSkillInfo().getSkillMessageMap().put(skillId, SkillElement.valueOf(skillId, 1));
    }

    /**
     * 返回所学技能
     */
    public Map<Integer, SkillElement> getLearnedSkill() {
        return skillEntity.getSkillInfo().getSkillMessageMap();
    }

    /**
     * 技能升级
     */
    public void upgrade(int skillId) {
        SkillElement skillElement = skillEntity.getSkillInfo().getSkillMessageMap().get(skillId);
        skillElement.setSkillLevel(skillElement.getSkillLevel() + 1);
    }

    /**
     * 移动技能到技能栏的postion位置
     */
    public void moveSkillToPosition(int skillId, int position) {
        Integer[] skills = skillEntity.getSkillInfo().getSkills();
        for (int i = 0; i < skills.length; i++) {
            if (skills[i] != null && skills[i] == skillId) {
                skills[i] = null;
            }
        }
        skills[position] = skillId;
    }

    /**
     * 获取技能栏
     */
    public Integer[] getSkillBar() {
        return skillEntity.getSkillInfo().getSkills();
    }

    /**
     * 获取技能栏里的id
     */
    public Integer getSkillIdInPosition(int position) {
        return getSkillBar()[position];
    }

    /**
     * 获取对应skill调用实体
     */
    public Skill getSkill(int skillId) {
        SkillElement skillElement = getSkillBySkillId(skillId);
        SkillResource skillResource = GetBean.getSkillManager().getSkillResourceBySkillId(skillId);
        Skill skill = Skill.valueOf(skillElement.getSkillId(), skillElement.getSkillLevel());
        return skill;
    }
}
