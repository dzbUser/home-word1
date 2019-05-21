package com.aiwan.util;

import com.aiwan.user.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户缓存暂用
 * */
public class UserCache {
    public static Map<String, User> userCache = new HashMap<>();
    //获取channel对应的用户
    public static Map<Integer,String> channelCache = new HashMap<>();

    public static User getUserByUsername(String username){
        return userCache.get(username);
    }

    public static void putUserByUsername(String username,User user){
        userCache.put(username,user);
    }

    public static void removeUserByUsername(String username){ userCache.remove(username); }

    public static String getUsernameByChannel(int hashCode){
        return channelCache.get(hashCode);
    }

    public static void putUsernameByChannel(int hashCode,String username){
        channelCache.put(hashCode,username);
    }

    public static void removeUsernameByChannel(int hashCode){
        channelCache.remove(hashCode);
    }
}
