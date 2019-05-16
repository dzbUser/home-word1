package com.aiwan.scenes.protocol;

import java.io.Serializable;

/**
 * 用户地图跳转静态资源接收协议类
 * */
public class CM_Shift implements Serializable {
    private String username;
    private short map;

    public CM_Shift(String username, short map) {
        this.username = username;
        this.map = map;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public short getMap() {
        return map;
    }

    public void setMap(short map) {
        this.map = map;
    }
}
