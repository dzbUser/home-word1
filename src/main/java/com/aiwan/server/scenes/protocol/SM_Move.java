package com.aiwan.server.scenes.protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * 用户移动协议发送类
 * */
public class SM_Move implements Serializable {
    private int targetX;
    private int targetY;
    private String mapMessage;

    public SM_Move(){}

    /**  获取对象 */
    public static SM_Move valueOf(int targetX, int targetY, String mapMessage){
        SM_Move sm_move = new SM_Move();
        sm_move.setMapMessage(mapMessage);
        sm_move.setTargetX(targetX);
        sm_move.setTargetY(targetY);
        return sm_move;
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
