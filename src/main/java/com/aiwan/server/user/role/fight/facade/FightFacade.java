package com.aiwan.server.user.role.fight.facade;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.fight.protocol.CM_UserSkill;
import com.aiwan.server.user.role.fight.protocol.CM_ViewFightBuff;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Controller;

/**
 * 战斗控制类
 *
 * @author dengzebiao
 * @since 2019.7.9
 */
@Controller
public class FightFacade {

    /**
     * 接收技能使用协议
     */
    public void useSkill(CM_UserSkill cm_userSkill, Session session) {
        GetBean.getFightService().userSkill(cm_userSkill.getrId(), cm_userSkill.getTargetId(), cm_userSkill.getBarPosition());
    }

    /**
     * 查看战斗buff
     */
    public void viewFightBuff(CM_ViewFightBuff cm_viewFightBuff, Session session) {
        if (session.getrId() == null) {
            return;
        }
        GetBean.getFightService().viewFightBuff(session.getrId(), session);
    }
}
