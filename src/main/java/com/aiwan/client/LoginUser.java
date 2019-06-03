package com.aiwan.client;

import java.util.List;

/**
 * @author dengzebiao
 * 保存登录成功的用户信息
 * */
public class LoginUser {

    private static String username = "";
    private static int map;
    private static String mapMessage;
    private static int currentX = -1;
    private static int currentY = -1;
    private static List<Long> roles;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        LoginUser.username = username;
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

    public static String getMapMessage() {
        return mapMessage;
    }

    public static void setMapMessage(String mapMessage) {
        LoginUser.mapMessage = mapMessage;
    }

    public static List<Long> getRoles() {
        return roles;
    }

    public static void setRoles(List<Long> roles) {
        LoginUser.roles = roles;
    }
}
