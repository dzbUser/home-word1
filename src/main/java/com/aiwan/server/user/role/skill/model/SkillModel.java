package com.aiwan.server.user.role.skill.model;

import com.aiwan.server.user.role.skill.entity.SkillEntity;
import com.aiwan.server.util.GetBean;

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
}
