package com.aiwan.scenes.service;

import com.aiwan.publicsystem.common.Session;
import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.scenes.protocol.CM_Move;
import com.aiwan.scenes.protocol.CM_Shift;
import com.aiwan.user.model.User;
import io.netty.channel.Channel;


public interface ScenesService {
//    public void move(CM_Move cm_move, Session session);
    public void move(Session session,CM_Move cm_move);

    public void shift(Session session, CM_Shift cm_shift);
}
