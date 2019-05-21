package com.aiwan.publicsystem.service;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChannelManager {
    //存储用户名与channel的映射
    private static Map<String, Channel> channelMap = new HashMap<>();
    //管理所有Channel
    private static Set<Channel> channels = new HashSet<>();
    public static Channel getChannelByUsername(String username){
        return channelMap.get(username);
    }

    public static void putChannel(String username, Channel channel){
        channelMap.put(username,channel);
    }

    public static void removeChannel(String username){
        channelMap.remove(username);
    }

    public static void putChannel(Channel channel){
        channels.add(channel);
    }

    public static void removeChannel(Channel channel){
        channels.remove(channel);
    }

    public static Set<Channel> getChannels(){
        return ChannelManager.channels;
    }
}
