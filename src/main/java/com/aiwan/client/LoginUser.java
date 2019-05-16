package com.aiwan.client;

public class LoginUser {

    private static String username = "";
    private static short map;
    private static String mapMessage;
    private static short currentX = -1;
    private static short currentY = -1;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        LoginUser.username = username;
    }

    public static short getCurrentX() {
        return currentX;
    }

    public static void setCurrentX(short currentX) {
        LoginUser.currentX = currentX;
    }

    public static short getCurrentY() {
        return currentY;
    }

    public static void setCurrentY(short currentY) {
        LoginUser.currentY = currentY;
    }

    public static short getMap() {
        return map;
    }

    public static void setMap(short map) {
        LoginUser.map = map;
    }

    public static String getMapMessage() {
        return mapMessage;
    }

    public static void setMapMessage(String mapMessage) {
        LoginUser.mapMessage = mapMessage;
    }
}
