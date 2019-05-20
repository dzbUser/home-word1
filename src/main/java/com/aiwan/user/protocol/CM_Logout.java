package com.aiwan.user.protocol;

import com.aiwan.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.util.Protocol;

import java.io.Serializable;

@ProtocolAnnotation(protocol = Protocol.LOGOUT)
public class CM_Logout implements Serializable {
    private String username;
//    @ProtocolHeart(protocolType = 7)
//    private int protocol;
//
//    public int getProtocol() {
//        return protocol;
//    }
//
//    public void setProtocol(int protocol) {
//        this.protocol = protocol;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
