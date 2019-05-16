package com.aiwan.scenes.protocol;

import java.io.Serializable;

public class SM_Move implements Serializable {
    private short targetX;
    private short targetY;
    private String mapMessage;

    public SM_Move(short targetX, short targetY, String mapMessage) {
        this.targetX = targetX;
        this.targetY = targetY;
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

    public String getMapMessage() {
        return mapMessage;
    }

    public void setMapMessage(String mapMessage) {
        this.mapMessage = mapMessage;
    }
}
