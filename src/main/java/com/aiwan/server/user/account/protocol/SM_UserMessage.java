package com.aiwan.server.user.account.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息发送协议
 * @author dengzebiao
 * */
public class SM_UserMessage implements Serializable {
    /** 登录状态*/
    private boolean status;
    /** 用户账号 */
    private String username;
    private int map;
    private int currentX;
    private int currentY;
    //是否创建角色
    private boolean created = true;
    //其他信息
    private String otherMessage;
    private String mapMessage;
    List<Long> roles;

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

    public boolean isCreated() {
        return created;
    }

    public SM_UserMessage setCreated(boolean created) {
        this.created = created;
        return this;
    }

    public String getOtherMessage() {
        return otherMessage;
    }

    public SM_UserMessage setOtherMessage(String otherMessage) {
        this.otherMessage = otherMessage;
        return this;
    }

    public List<Long> getRoles() {
        return roles;
    }

    public SM_UserMessage setRoles(List<Long> roles) {
        this.roles = roles;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public SM_UserMessage setStatus(boolean status) {
        this.status = status;
        return this;
    }
}
