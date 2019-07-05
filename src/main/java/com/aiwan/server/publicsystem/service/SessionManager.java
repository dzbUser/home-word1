package com.aiwan.server.publicsystem.service;

import com.aiwan.server.publicsystem.common.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dengzebiao
 * Session管理类
 * */
public class SessionManager {
    //hashcode与session的映射
    private static Map<Integer, Session> sessionMap = new ConcurrentHashMap<Integer, Session>();

    //username与session的映射
    private static Map<String,Session> userMap = new ConcurrentHashMap<String,Session>();
    public static void putSessionByHashCode(Integer hashcode,Session session){
        sessionMap.put(hashcode,session);
    }

    public static void removeSessionByHashCode(Integer hashcode){
        sessionMap.remove(hashcode);
    }

    public static Session getSessionByHashCode(Integer hashcode){
        return sessionMap.get(hashcode);
    }

    public static void putSessionByUsername(String accountId, Session session) {
        userMap.put(accountId, session);
    }

    public static void removeSessionByAccountId(String accountId) {
        userMap.remove(accountId);
    }

    public static Session getSessionByAccountId(String AccountId) {
        return userMap.get(AccountId);
    }
}
