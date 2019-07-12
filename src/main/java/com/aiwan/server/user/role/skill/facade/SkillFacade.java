package com.aiwan.server.user.role.skill.facade;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.skill.protocol.*;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Controller;

/**
 * @author dengzebiao
 * 技能协议包接收类
 */
@Controller
public class SkillFacade {

    /**
     * 学习技能
     *
     * @param cm_learnSkill 协议类
     * @param session       会话
     */
    public void learnSkill(CM_LearnSkill cm_learnSkill, Session session) {
        GetBean.getSkillService().learnSkill(cm_learnSkill.getRId(), cm_learnSkill.getSkillId(), session);
    }

    /**
     * 查看已学技能
     * @param cm_viewLearnSkill 协议类
     * @param session   会话
     */
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

    /**
     * 查看技能栏
     */
    public void viewSkillBar(CM_ViewSkillBar cm_viewSkillBar, Session session) {
        GetBean.getSkillService().viewSkillBar(cm_viewSkillBar.getrId(), session);
    }
}
