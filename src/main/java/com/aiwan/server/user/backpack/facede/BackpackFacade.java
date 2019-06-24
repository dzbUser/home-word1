package com.aiwan.server.user.backpack.facede;

import com.aiwan.server.prop.protocol.CM_PropUse;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.backpack.protocol.CM_DropProps;
import com.aiwan.server.user.backpack.protocol.CM_Equip;
import com.aiwan.server.user.backpack.protocol.CM_ObtainProp;
import com.aiwan.server.user.backpack.protocol.CM_ViewBackpack;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Controller;

/**
 * @author dengzebiao
 * @since 2019.6.19
 * 背包协议接收类
 * */
@Controller
public class BackpackFacade {
    /** 查看背包
     * @param cm_viewBackpack 查看背包协议类
     * @param session 会话
     * */
    public void viewBackpack(CM_ViewBackpack cm_viewBackpack, Session session){
        GetBean.getBackpackService().viewBackpack(cm_viewBackpack.getAccountId(), session);
    }

    /** 道具使用 */
    public void propUse(CM_PropUse cm_propUser, Session session){
        GetBean.getBackpackService().propUse(cm_propUser.getAccountId(), cm_propUser.getPosition(), cm_propUser.getNum(), cm_propUser.getrId(), session);
    }

    /** 添加道具到背包 */
    public void addPropToBack(CM_ObtainProp cm_obtainProp, Session session){
        GetBean.getBackpackService().addPropToBack(cm_obtainProp.getAccountId(), cm_obtainProp.getId(), cm_obtainProp.getNum(), session);
    }

    /**
     * 丢弃道具
     */
    public void DropProps(CM_DropProps cm_dropProps, Session session) {
        GetBean.getBackpackService().dropProps(cm_dropProps.getAccountId(), cm_dropProps.getPosition(), cm_dropProps.getNum(), session);
    }

    /**
     * 使用装备
     */
    public void userEquipment(CM_Equip cm_equip, Session session) {
        GetBean.getBackpackService().useEquipment(cm_equip.getAccountId(), cm_equip.getPosition(), cm_equip.getrId(), session);
    }
}
