package com.aiwan.server.user.role.skill.facade;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.skill.protocol.CM_LearnSkill;
import com.aiwan.server.user.role.skill.protocol.CM_MoveSkill;
import com.aiwan.server.user.role.skill.protocol.CM_UpgradeSkill;
import com.aiwan.server.user.role.skill.protocol.CM_ViewLearnSkill;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Controller;

/**
 * @author dengzebiao
 * 技能协议包接收类
 */
@Controller
public class SkillFacade {
    public void learnSkill(CM_LearnSkill cm_learnSkill, Session session) {
        GetBean.getSkillService().learnSkill(cm_learnSkill.getRId(), cm_learnSkill.getSkillId(), cm_learnSkill.getSkillTypeId(), session);
    }

    public void viewLearnedSkill(CM_ViewLearnSkill cm_viewLearnSkill, Session session) {
        GetBean.getSkillService().viewLearnedSkill(cm_viewLearnSkill.getRId(), session);
    }

    /**
     * 升级
     */
    public void upgrade(CM_UpgradeSkill cm_upgradeSkill, Session session) {
        GetBean.getSkillService().upgradeSkill(cm_upgradeSkill.getrId(), cm_upgradeSkill.getSkillId(), session);
    }

    /**
     * 移动技能位置
     */
    public void moveSkillPosition(CM_MoveSkill cm_moveSkill, Session session) {
        GetBean.getSkillService().moveSkillToPosition(cm_moveSkill.getrId(), cm_moveSkill.getSkillId(), cm_moveSkill.getPosition(), session);
    }
}
