package com.aiwan.server.user.role.skill.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * @since 2019.7.1
 * 移动技能位置
 */
@ProtocolAnnotation(protocol = Protocol.MOVESKILL)
public class CM_MoveSkill implements Serializable {

    /**
     * 角色id
     */
    private Long rId;

    /**
     * 技能id
     */
    private int skillId;

    /**
     * 移动位置
     */
    private int position;

    public static CM_MoveSkill valueOf(Long rId, int skillId, int position) {
        CM_MoveSkill cm_moveSkill = new CM_MoveSkill();
        cm_moveSkill.setrId(rId);
        cm_moveSkill.setPosition(position);
        cm_moveSkill.setSkillId(skillId);
        return cm_moveSkill;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
