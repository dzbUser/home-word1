package com.aiwan.server.world.scenes.protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * 地图跳转协议类
 * */
public class SM_Shift implements Serializable {
    private int targetX;
    private int targetY;
    private int map;


    /** 获取对象 */
    public static SM_Shift valueOf(int targetX, int targetY, int map) {
        SM_Shift sm_shift = new SM_Shift();
        sm_shift.setMap(map);
        sm_shift.setTargetX(targetX);
        sm_shift.setTargetY(targetY);
        return sm_shift;
    }

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

}
