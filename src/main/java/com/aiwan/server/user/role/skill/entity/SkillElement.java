package com.aiwan.server.user.role.skill.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 存储入库技能单位
 *
 * @author dengzebiao
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
public class SkillElement {

    /**
     * 技能id
     */
    private int skillId;

    /**
     * 技能等级
     */
    private int skillLevel;

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public static SkillElement valueOf(int skillId, int skillLevel) {
        SkillElement skillElement = new SkillElement();
        skillElement.setSkillId(skillId);
        skillElement.setSkillLevel(skillLevel);
        return skillElement;
    }
}
