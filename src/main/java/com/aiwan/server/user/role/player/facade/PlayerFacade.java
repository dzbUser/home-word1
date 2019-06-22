package com.aiwan.server.user.role.player.facade;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.account.protocol.CM_CreateRole;
import com.aiwan.server.user.role.player.protocol.CM_RoleMessage;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Controller;

/**
 * @author dengzebiao
 * @since 2019.6.19
 * 角色协议接收类
 * */
@Controller
public class PlayerFacade {


    /**
     * 获取角色信息
     * */
    public void getRoleMessage(final CM_RoleMessage cm_roleMessage, final Session session){
        GetBean.getRoleService().getRoleMessage(cm_roleMessage.getAccountId(), cm_roleMessage.getrId(), session);
    }
}
