package com.aiwan.server.publicsystem.common;

import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.user.model.User;
import io.netty.channel.Channel;

/**
 * @author dengzebiao
 * 用户session
 * */
public class Session {
    private Channel channel;
    private User user;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void messageSend(DecodeData decodeData){
        channel.writeAndFlush(decodeData);
    }
}
