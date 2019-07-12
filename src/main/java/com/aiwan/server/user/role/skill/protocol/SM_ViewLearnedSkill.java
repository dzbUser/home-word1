package com.aiwan.server.user.role.skill.protocol;

import com.aiwan.server.user.role.skill.protocol.element.SkillElementMessage;

import java.io.Serializable;
import java.util.List;

/**
 * @author dengzebiao
 * 查看已学技能的返回
 */
public class SM_ViewLearnedSkill implements Serializable {
    private List<SkillElementMessage> list;

    public List<SkillElementMessage> getList() {
        return list;
    }

    public void setList(List<SkillElementMessage> list) {
        this.list = list;
    }

    public static SM_ViewLearnedSkill valueOf(List<SkillElementMessage> list) {
        SM_ViewLearnedSkill sm_viewLearnedSkill = new SM_ViewLearnedSkill();
        sm_viewLearnedSkill.setList(list);
        return sm_viewLearnedSkill;
    }
}
