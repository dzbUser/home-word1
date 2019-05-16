package com.aiwan.scenes.protocol;

import java.io.Serializable;

/**
 * 地图跳转协议类
 * */
public class SM_Shift implements Serializable {
    private short targetX;
    private short targetY;
    private short map;
    private String mapMessage;

    public SM_Shift(short targetX, short targetY, short map, String mapMessage) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.map = map;
        this.mapMessage = mapMessage;
    }

    public short getTargetX() {
        return targetX;
    }

    public void setTargetX(short targetX) {
        this.targetX = targetX;
    }

    public short getTargetY() {
        return targetY;
    }

    public void setTargetY(short targetY) {
        this.targetY = targetY;
    }

    public short getMap() {
        return map;
    }

    public void setMap(short map) {
        this.map = map;
    }

    public String getMapMessage() {
        return mapMessage;
    }

    public void setMapMessage(String mapMessage) {
        this.mapMessage = mapMessage;
    }
}
