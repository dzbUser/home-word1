package com.aiwan.scenes.service;

import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.scenes.protocol.CM_Move;
import com.aiwan.scenes.protocol.CM_Shift;

public interface ScenesService {
    public void move(CM_Move cm_move);

    public void shift(CM_Shift cm_shift);
}
