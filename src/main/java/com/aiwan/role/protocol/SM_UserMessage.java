package com.aiwan.role.protocol;

import java.io.Serializable;

public class SM_UserMessage implements Serializable {
    private String username;
    private Short map;
    private Short currentX;
    private Short currentY;

    public short getMap() {
        return map;
    }
    public void setMap(short map) {
        this.map = map;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
