package com.aiwan.publicsystem.service;

import com.aiwan.publicsystem.common.Session;

import java.util.HashMap;
import java.util.Map;

/**
 * Session管理类
 * */
public class SessionManager {
    //hashcode与session的映射
    private static Map<Integer, Session> sessionMap = new HashMap<>();

    //username与session的映射
    private static Map<String,Session> userMap = new HashMap<>();
    public static void putSessionByHashCode(Integer hashcode,Session session){
        sessionMap.put(hashcode,session);
    }

    public static void removeSessionByHashCode(Integer hashcode){
        sessionMap.remove(hashcode);
    }

    public static Session getSessionByHashCode(Integer hashcode){
        return sessionMap.get(hashcode);
    }

    public static void putSessionByUsername(String username,Session session){
        userMap.put(username,session);
    }

    public static void removeSessionByUsername(String username){
        userMap.remove(username);
    }

    public static Session getSessionByUsername(String username){
        return userMap.get(username);
    }
}
