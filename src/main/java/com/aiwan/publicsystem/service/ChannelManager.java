package com.aiwan.publicsystem.service;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class ChannelManager {
    private static Map<String, Channel> channelMap = new HashMap<>();
    public static Channel getChannelByUsername(String username){
        return channelMap.get(username);
    }

    public static void putChannel(String username, Channel channel){
        channelMap.put(username,channel);
    }

    public static void removeChannel(String username){
        channelMap.remove(username);
    }
}
