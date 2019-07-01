package com.aiwan.server.user.role.skill.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * @since 2019.7.1
 * 查看用户所学技能
 */
@ProtocolAnnotation(protocol = Protocol.VIEWLEARNSKILL)
public class CM_ViewLearnSkill implements Serializable {
    private Long rId;

    public Long getRId() {
        return rId;
    }

    public void setRId(Long rId) {
        this.rId = rId;
    }

    public static CM_ViewLearnSkill valueOf(Long rId) {
        CM_ViewLearnSkill cm_viewLearnSkill = new CM_ViewLearnSkill();
        cm_viewLearnSkill.setRId(rId);
        return cm_viewLearnSkill;
    }
}
