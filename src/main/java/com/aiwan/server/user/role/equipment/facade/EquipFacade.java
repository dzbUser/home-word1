package com.aiwan.server.user.role.equipment.facade;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.equipment.protocol.CM_ViewEquipBar;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Controller;

/**
 * @author dengzebiao
 * @since 2019.16.19
 * 角色协议类
 * */
@Controller
public class EquipFacade {
    /**查看角色装备 */
    public void viewEquip(CM_ViewEquipBar cm_viewEquipBar, Session session){
        GetBean.getEquipmentService().viewEquip(cm_viewEquipBar,session);
    }
}
