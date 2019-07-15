package com.aiwan.server.publicsystem.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;

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

    /**
     * 发送提示信息
     */
    public static void sendPromptMessage(long rId, int promptCode, String message) {
        Role role = GetBean.getRoleManager().load(rId);
        if (role != null) {
            getSessionByAccountId(role.getAccountId()).sendPromptMessage(promptCode, message);
        }
    }

    public static void sendPromptMessage(String accountId, int promptCode, String message) {
        getSessionByAccountId(accountId).sendPromptMessage(promptCode, message);
    }

    public static void sendMessageByRid(long rId, int statusCode, Object obj) {
        Role role = GetBean.getRoleManager().load(rId);
        if (role != null) {
            getSessionByAccountId(role.getAccountId()).messageSend(statusCode, obj);
        }
    }
}
