package com.aiwan.publicsystem;

import java.io.Serializable;

public class CM_Move implements Serializable {
    private short currentX;
    private short currentY;
    private short targetX;
    private short targetY;

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
}