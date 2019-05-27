package com.aiwan.user.protocol;

import com.aiwan.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.util.Protocol;

import java.io.Serializable;

/**
 * 登录收协议
 * */
@ProtocolAnnotation(protocol = Protocol.USERMESSAGE)
public class CM_UserMessage implements Serializable {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
