package com.aiwan.util;

import redis.clients.jedis.Jedis;

/**
 * redis操作
 * */
public class RedisOperating {
    private static Jedis jedis;

    public static void init(String host){
        RedisOperating.jedis = new Jedis(host);
        System.out.println("Connection to server sucessfully");
    }

    public static void putCache(String key,String value){
        RedisOperating.jedis.set(key,value);
    }

    public static String getCache(String key){
        return RedisOperating.jedis.get(key);
    }

    public static void removeCache(String key){
        RedisOperating.jedis.del(key);
    }
}
