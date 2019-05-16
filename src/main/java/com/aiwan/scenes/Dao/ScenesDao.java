package com.aiwan.scenes.Dao;

import com.aiwan.scenes.protocol.CM_Move;
import com.aiwan.scenes.protocol.CM_Shift;

public interface ScenesDao {
    public void updateUserPosition(CM_Move cm_move);

    public void updateMapPosition(CM_Shift cm_shift,short x,short y);
}
