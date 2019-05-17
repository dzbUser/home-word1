package com.aiwan.util;

import com.aiwan.user.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户缓存暂用
 * */
public class UserCache {
    public static Map<String, User> userCache = new HashMap<>();

    public static User getUserByUsername(String username){
        return userCache.get(username);
    }

    public static void putUserByUsername(String username,User user){
        userCache.put(username,user);
    }
}
