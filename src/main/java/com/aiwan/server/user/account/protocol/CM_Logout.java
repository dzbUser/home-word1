package com.aiwan.server.user.account.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

@ProtocolAnnotation(protocol = Protocol.LOGOUT)
public class CM_Logout implements Serializable {
    private String username;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
