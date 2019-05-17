package com.aiwan.scenes.protocol;

import java.io.Serializable;

/**
 * 用户移动协议发送类
 * */
public class SM_Move implements Serializable {
    private int targetX;
    private int targetY;
    private String mapMessage;

    public SM_Move(int targetX, int targetY, String mapMessage) {
        this.targetX = targetX;
        this.targetY = targetY;
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

    public String getMapMessage() {
        return mapMessage;
    }

    public void setMapMessage(String mapMessage) {
        this.mapMessage = mapMessage;
    }
}
