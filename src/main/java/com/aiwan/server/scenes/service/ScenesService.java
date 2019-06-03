package com.aiwan.server.scenes.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.scenes.protocol.CM_Move;
import com.aiwan.server.scenes.protocol.CM_Shift;


public interface ScenesService {
//    public void move(CM_Move cm_move, Session session);
    public void move(Session session,CM_Move cm_move);

    public void shift(Session session, CM_Shift cm_shift);
}
