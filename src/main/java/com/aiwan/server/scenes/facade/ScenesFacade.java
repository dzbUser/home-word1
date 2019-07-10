package com.aiwan.server.scenes.facade;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.scenes.protocol.CM_Move;
import com.aiwan.server.scenes.protocol.CM_Shift;
import com.aiwan.server.scenes.protocol.CM_ViewAllUnitInMap;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Controller;

/**
 * @author dengzebiao
 * @since 2019.6.19
 * 用户场景协议接受类
 * */
@Controller
public class ScenesFacade {
    /** 角色移动 */
    public void move(final CM_Move cm_move, final Session session){
        GetBean.getScenesService().move(cm_move.getrId(), cm_move.getTargetX(), cm_move.getTargetY(), session);
    }

    /** 角色地图跳转 */
    public void shift(final CM_Shift cm_shift, final Session session){
        GetBean.getScenesService().shift(cm_shift.getrId(), cm_shift.getMap(), session);
    }

    /**
     * 查看地图所有单位
     */
    public void viewUnitInMap(CM_ViewAllUnitInMap cm_viewAllUnitInMap, Session session) {
        GetBean.getScenesService().viewUnitInMap(cm_viewAllUnitInMap.getMapId(), session);
    }
}
