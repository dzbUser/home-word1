package com.aiwan.role.protocol;

import java.io.Serializable;

/**
 * 用户账号与密码接收协议
 * */
public class CM_UserMessage implements Serializable {
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
