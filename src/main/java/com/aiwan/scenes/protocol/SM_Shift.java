package com.aiwan.scenes.protocol;

import java.io.Serializable;

/**
 * 地图跳转协议类
 * */
public class SM_Shift implements Serializable {
    private int targetX;
    private int targetY;
    private int map;
    private String mapMessage;

    public SM_Shift(int targetX, int targetY, int map, String mapMessage) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.map = map;
        this.mapMessage = mapMessage;
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
