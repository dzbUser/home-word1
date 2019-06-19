package com.aiwan.server.user.role.mount.facade;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.mount.protocol.CM_MountUpgrade;
import com.aiwan.server.user.role.mount.protocol.CM_ViewMount;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Controller;

/**
 * @author dengzebiao
 * @since 2019.6.19
 * 坐骑协议接收类
 * */
@Controller
public class MountFacade {
    /** 查看坐骑 */
    public void viewMount(CM_ViewMount cm_viewMount, Session session){
        GetBean.getMountService().viewMount(cm_viewMount,session);
    }

    /** 坐骑升阶丹 */
    public void mountUpgrade(CM_MountUpgrade cm_mountUpgrade, Session session){
        GetBean.getMountService().mountUpgrade(cm_mountUpgrade,session);
    }
}
