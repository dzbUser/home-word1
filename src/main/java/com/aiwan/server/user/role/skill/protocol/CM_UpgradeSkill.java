package com.aiwan.server.user.role.skill.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * @since 2019.7.1
 * 技能升级
 */
@ProtocolAnnotation(protocol = Protocol.UPGRADESKILL)
public class CM_UpgradeSkill implements Serializable {

    /**
     * 角色id
     */
    private Long rId;

    /**
     * 技能id
     */
    private int SkillId;

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public int getSkillId() {
        return SkillId;
    }

    public void setSkillId(int skillId) {
        SkillId = skillId;
    }

    public static CM_UpgradeSkill valueOf(Long rId, int skillId) {
        CM_UpgradeSkill cm_upgradeSkill = new CM_UpgradeSkill();
        cm_upgradeSkill.setrId(rId);
        cm_upgradeSkill.setSkillId(skillId);
        return cm_upgradeSkill;
    }
}
