package com.aiwan.user.service;

import com.aiwan.publicsystem.common.Session;
import com.aiwan.scenes.protocol.CM_Move;
import com.aiwan.scenes.protocol.CM_Shift;
import com.aiwan.user.model.User;
import com.aiwan.user.protocol.*;
import io.netty.channel.Channel;


public interface UserService {
//    public User getUserByUid(int uid);

    public void login(CM_Login userMessage, Session session);

    public void registUser(CM_Registered userMessage, Session session);

    public void logout(CM_Logout userMessage, Session session);

    public void hLogin(CM_HLogin cm_hlogin,Session session);

    public void move(CM_Move cm_move,Session session);

    public void shift(CM_Shift cm_shift, Session session);
}
