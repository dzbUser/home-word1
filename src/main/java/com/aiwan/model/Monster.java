package com.aiwan.model;

public class Monster {
    private String name;
    private short currentX;
    private short currentY;

    public Monster(String name, short currentX, short currentY) {
        this.name = name;
        this.currentX = currentX;
        this.currentY = currentY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}