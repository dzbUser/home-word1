package com.aiwan.user.service;

import com.aiwan.user.entity.User;
import com.aiwan.user.protocol.*;
import io.netty.channel.Channel;


public interface UserService {
    public User getUserByUid(int uid);

    public void login(CM_Login userMessage, Channel channel);

    public void registUser(CM_Registered userMessage, Channel channel);

    public void logout(CM_Logout userMessage, Channel channel);

    public void hLogin(CM_HLogin cm_hlogin,Channel channel);
}
