package com.aiwan.server.user.role.skill.protocol.element;

import java.io.Serializable;

/**
 * 协议返回的元素类
 *
 * @author dengzebiao
 */
public class SkillElementMessage implements Serializable {
    /**
     * 技能id
     */
    private int skillId;

    /**
     * 技能等级
     */
    private int level;

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public static SkillElementMessage valueOf(int skillId, int level) {
        SkillElementMessage skillElementMessage = new SkillElementMessage();
        skillElementMessage.setSkillId(skillId);
        skillElementMessage.setLevel(level);
        return skillElementMessage;
    }
}
