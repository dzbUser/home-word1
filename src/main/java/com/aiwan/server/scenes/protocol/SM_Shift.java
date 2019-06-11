package com.aiwan.server.scenes.protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * 地图跳转协议类
 * */
public class SM_Shift implements Serializable {
    private int targetX;
    private int targetY;
    private int map;
    private String mapMessage;


    /** 获取对象 */
    public static SM_Shift valueOf(int targetX, int targetY, int map, String mapMessage){
        SM_Shift sm_shift = new SM_Shift();
        sm_shift.setMap(map);
        sm_shift.setTargetX(targetX);
        sm_shift.setTargetY(targetY);
        sm_shift.setMapMessage(mapMessage);
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

    public String getMapMessage() {
        return mapMessage;
    }

    public void setMapMessage(String mapMessage) {
        this.mapMessage = mapMessage;
    }
}
