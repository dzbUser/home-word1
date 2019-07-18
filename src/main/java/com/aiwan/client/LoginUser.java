package com.aiwan.client;

import com.aiwan.server.world.scenes.protocol.SM_MapMessage;

import java.util.List;

/**
 * @author dengzebiao
 * 保存登录成功的用户信息
 * */
public class LoginUser {


    private static String accountId = "";
    private static int map;
    private static int currentX = -1;
    private static int currentY = -1;
    private static List<Long> roles;

    /**
     * 地图信息
     */
    private static SM_MapMessage mapMessage;

    public static String getAccountId() {
        return accountId;
    }

    public static void setAccountId(String accountId) {
        LoginUser.accountId = accountId;
    }

    public static int getCurrentX() {
        return currentX;
    }

    public static void setCurrentX(int currentX) {
        LoginUser.currentX = currentX;
    }

    public static int getCurrentY() {
        return currentY;
    }

    public static void setCurrentY(int currentY) {
        LoginUser.currentY = currentY;
    }

    public static int getMap() {
        return map;
    }

    public static void setMap(int map) {
        LoginUser.map = map;
    }

    public static List<Long> getRoles() {
        return roles;
    }

    public static void setRoles(List<Long> roles) {
        LoginUser.roles = roles;
    }

    public static SM_MapMessage getMapMessage() {
        return mapMessage;
    }

    public static void setMapMessage(SM_MapMessage mapMessage) {
        LoginUser.mapMessage = mapMessage;
    }
}
