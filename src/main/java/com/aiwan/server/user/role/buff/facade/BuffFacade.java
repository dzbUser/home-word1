package com.aiwan.server.user.role.buff.facade;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.buff.protocol.CM_ViewBuff;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Controller;

/**
 * buff控制层
 *
 * @author dengzebiao
 * @since 2019.7.5
 */
@Controller
public class BuffFacade {


    /**
     * 查看玩家身上的buff
     */
    public void viewBuff(CM_ViewBuff cm_viewBuff, Session session) {
        GetBean.getBuffService().viewBuff(cm_viewBuff.getrId(), session);
    }
}
