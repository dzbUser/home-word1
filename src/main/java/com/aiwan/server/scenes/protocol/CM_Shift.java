package com.aiwan.server.scenes.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * 用户地图跳转静态资源接收协议类
 * */
@ProtocolAnnotation(protocol = Protocol.SHIFT)
public class CM_Shift implements Serializable {
    private String username;
    private int map;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }
}
