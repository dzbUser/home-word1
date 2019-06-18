package com.aiwan.server.user.role.player.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * @author dengzebiao
 * 角色创建成功返回
 * */
public class SM_CreateRole implements Serializable {
    private List<Long> roles;
    private String message;
    private String mapMessage;

    public List<Long> getRoles() {
        return roles;
    }

    public SM_CreateRole setRoles(List<Long> roles) {
        this.roles = roles;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SM_CreateRole setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMapMessage() {
        return mapMessage;
    }

    public SM_CreateRole setMapMessage(String mapMessage) {
        this.mapMessage = mapMessage;
        return this;
    }
}
