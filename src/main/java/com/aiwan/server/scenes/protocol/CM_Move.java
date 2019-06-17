package com.aiwan.server.scenes.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;
import org.openxmlformats.schemas.presentationml.x2006.main.CmAuthorLstDocument;

import java.io.Serializable;

/**
 * @author dengzebiao
 * 用户移动数据类接收协议
 * */
@ProtocolAnnotation(protocol = Protocol.MOVE)
public class CM_Move implements Serializable {
    private int currentX;
    private int currentY;
    private int targetX;
    private int targetY;
    private String username;

    public static CM_Move valueOf(int currentX,int currentY,int targetX,int targetY,String username){
        CM_Move cm_move = new CM_Move();
        cm_move.setCurrentX(currentX);
        cm_move.setCurrentY(currentY);
        cm_move.setTargetX(targetX);
        cm_move.setTargetY(targetY);
        cm_move.setUsername(username);
        return cm_move;
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
