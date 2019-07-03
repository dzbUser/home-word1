package com.aiwan.server.scenes.protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * 用户移动协议发送类
 * */
public class SM_Move implements Serializable {
    private int targetX;
    private int targetY;
    /** 状态
     * 1.移动成功
     * 0.移动失败
     * */
    private int status;

    public SM_Move(){}

    /**  获取对象 */
    public static SM_Move valueOf(int targetX, int targetY, int status) {
        SM_Move sm_move = new SM_Move();
        sm_move.setTargetX(targetX);
        sm_move.setTargetY(targetY);
        sm_move.setStatus(status);
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

    public int getStatus() {
        return status;
    }

    public SM_Move setStatus(int status) {
        this.status = status;
        return this;
    }
}
