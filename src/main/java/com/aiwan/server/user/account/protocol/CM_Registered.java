package com.aiwan.server.user.account.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

@ProtocolAnnotation(protocol = Protocol.REGIST)
public class CM_Registered implements Serializable {

    private String username;
    private String password;
    private String hpassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHpassword() {
        return hpassword;
    }

    public void setHpassword(String hpassword) {
        this.hpassword = hpassword;
    }
}
