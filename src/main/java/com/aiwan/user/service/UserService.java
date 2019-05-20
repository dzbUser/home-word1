package com.aiwan.user.service;

import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.user.entity.User;
import com.aiwan.user.protocol.CM_Login;
import com.aiwan.user.protocol.CM_Logout;
import com.aiwan.user.protocol.CM_Registered;
import com.aiwan.user.protocol.CM_UserMessage;
import io.netty.channel.Channel;


public interface UserService {
    public User getUserByUid(int uid);

    public void login(CM_Login userMessage, Channel channel);

    public void registUser(CM_Registered userMessage, Channel channel);

    public void logout(CM_Logout userMessage, Channel channel);
}
