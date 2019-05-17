package com.aiwan.user.service;

import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.user.entity.User;
import com.aiwan.user.protocol.CM_UserMessage;
import io.netty.channel.Channel;


public interface UserService {
    public User getUserByUid(int uid);

    public void login(CM_UserMessage userMessage, Channel channel);

    public void registUser(CM_UserMessage userMessage,Channel channel);

    public void logout(CM_UserMessage userMessage);
}
