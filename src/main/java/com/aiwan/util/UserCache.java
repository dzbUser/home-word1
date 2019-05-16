package com.aiwan.util;

import com.aiwan.role.entity.User;

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
}
