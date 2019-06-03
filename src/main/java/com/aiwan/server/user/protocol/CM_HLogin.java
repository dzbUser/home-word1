package com.aiwan.server.user.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

@ProtocolAnnotation(protocol = Protocol.HLOGIN)
public class CM_HLogin implements Serializable {

    private String username;
    private String hpassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHpassword() {
        return hpassword;
    }

    public void setHpassword(String hpassword) {
        this.hpassword = hpassword;
    }
}