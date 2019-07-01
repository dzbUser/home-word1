package com.aiwan.server.user.role.skill.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * 学习新技能
 */
@ProtocolAnnotation(protocol = Protocol.LEAENSKILL)
public class CM_LearnSkill implements Serializable {

    /**
     * 角色id
     */
    private Long rId;


    /**
     * 技能id
     */
    private int skillId;

    /**
     * 技能类型id
     */
    private int skillTypeId;

    public static CM_LearnSkill valueOf(Long rId, int skillId, int skillTypeId) {
        CM_LearnSkill cm_learnSkill = new CM_LearnSkill();
        cm_learnSkill.setRId(rId);
        cm_learnSkill.setSkillId(skillId);
        cm_learnSkill.setSkillTypeId(skillTypeId);
        return cm_learnSkill;
    }

    public Long getRId() {
        return rId;
    }

    public void setRId(Long rId) {
        this.rId = rId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getSkillTypeId() {
        return skillTypeId;
    }

    public void setSkillTypeId(int skillTypeId) {
        this.skillTypeId = skillTypeId;
    }
}
