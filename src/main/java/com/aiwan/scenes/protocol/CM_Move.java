package com.aiwan.scenes.protocol;

import java.io.Serializable;

/**
 * 用户移动数据类接收协议
 * */
public class CM_Move implements Serializable {
    private int currentX;
    private int currentY;
    private int targetX;
    private int targetY;
    private String username;

    public CM_Move(int currentX, int currentY, int targetX, int targetY, String username) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.username = username;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
