package com.aiwan.server.user.backpack.facede;

import com.aiwan.server.prop.protocol.CM_PropUse;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.backpack.protocol.CM_DropProps;
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
        GetBean.getBackpackService().viewBackpack(cm_viewBackpack,session);
    }

    /** 道具使用 */
    public void propUse(CM_PropUse cm_propUser, Session session){
        GetBean.getBackpackService().propUse(cm_propUser,session);
    }

    /** 添加道具到背包 */
    public void addPropToBack(CM_ObtainProp cm_obtainProp, Session session){
        GetBean.getBackpackService().addPropToBack(cm_obtainProp,session);
    }

    /**
     * 丢弃道具
     */
    public void DropProps(CM_DropProps cm_dropProps, Session session) {
        GetBean.getBackpackService().dropProps(cm_dropProps.getAccountId(), cm_dropProps.getPosition(), cm_dropProps.getNum(), session);
    }
}
