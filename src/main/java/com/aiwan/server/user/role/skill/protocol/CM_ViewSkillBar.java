package com.aiwan.server.user.role.skill.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * 查看技能栏
 */
@ProtocolAnnotation(protocol = Protocol.VIEWSKILLBAR)
public class CM_ViewSkillBar implements Serializable {
    private Long rId;

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public static CM_ViewSkillBar valueOf(Long rId) {
        CM_ViewSkillBar cm_viewSkillBar = new CM_ViewSkillBar();
        cm_viewSkillBar.setrId(rId);
        return cm_viewSkillBar;
    }
}
