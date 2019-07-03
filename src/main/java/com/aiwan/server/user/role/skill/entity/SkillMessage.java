package com.aiwan.server.user.role.skill.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 存储入库技能信息
 *
 * @author dengzebiao
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
public class SkillMessage {

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

    public static SkillMessage valueOf(int skillId, int skillLevel) {
        SkillMessage skillMessage = new SkillMessage();
        skillMessage.setSkillId(skillId);
        skillMessage.setSkillLevel(skillLevel);
        return skillMessage;
    }
}
