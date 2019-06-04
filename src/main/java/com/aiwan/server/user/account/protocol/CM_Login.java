package com.aiwan.server.user.account.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

@ProtocolAnnotation(protocol = Protocol.LOGIN)
public class CM_Login implements Serializable {
    private static final long serialVersionUID = -5809782578272943999L;
    private String username;
    private String password;
//    @ProtocolHeart(protocolType = 1)
//    private int protocol;
//
//    public int getProtocol() {
//        return protocol;
//    }
//
//    public void setProtocol(int protocol) {
//        this.protocol = protocol;
//    }

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