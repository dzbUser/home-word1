package com.aiwan.user.protocol;

import java.io.Serializable;

/**
 * 用户信息发送协议
 * */
public class SM_UserMessage implements Serializable {
    private String username;
    private int map;
    private int currentX;
    private int currentY;
    private String mapMessage;

    public int getMap() {
        return map;
    }
    public void setMap(int map) {
        this.map = map;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMapMessage() {
        return mapMessage;
    }

    public void setMapMessage(String mapMessage) {
        this.mapMessage = mapMessage;
    }
}
