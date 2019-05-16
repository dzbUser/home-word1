package com.aiwan.scenes.protocol;

import java.io.Serializable;

/**
 * 用户移动数据类接收协议
 * */
public class CM_Move implements Serializable {
    private short currentX;
    private short currentY;
    private short targetX;
    private short targetY;
    private String username;

    public CM_Move(short currentX, short currentY, short targetX, short targetY, String username) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.username = username;
    }

    public short getCurrentX() {
        return currentX;
    }

    public void setCurrentX(short currentX) {
        this.currentX = currentX;
    }

    public short getCurrentY() {
        return currentY;
    }

    public void setCurrentY(short currentY) {
        this.currentY = currentY;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
