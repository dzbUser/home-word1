package com.aiwan.user.protocol;

import com.aiwan.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.util.Protocol;

import java.io.Serializable;

@ProtocolAnnotation(protocol = Protocol.REGIST)
public class CM_Registered implements Serializable {

    private String username;
    private String password;

//    @ProtocolHeart(protocolType = 2)
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
