package com.aiwan.server.scenes.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.scenes.protocol.CM_Move;
import com.aiwan.server.scenes.protocol.CM_Shift;

/**
 * @author dengzebiao
 * 场景的移动和跳转
 * */
public interface ScenesService {
    /** 角色移动 */
    void move(final CM_Move cm_move, final Session session);

    /** 角色地图跳转 */
    void shift(final CM_Shift cm_shift, final Session session);
}
