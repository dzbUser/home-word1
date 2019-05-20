package com.aiwan.user.protocol;

import java.io.Serializable;

/**
 * 登录收协议
 * */
//@ProtocolAnnotation(id = Protocol.LOGIN)
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
