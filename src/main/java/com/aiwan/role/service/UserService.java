package com.aiwan.role.service;

import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.role.entity.User;
import com.aiwan.role.protocol.CM_UserMessage;

public interface UserService {
    public User getUserByUid(int uid);

    public DecodeData login(CM_UserMessage userMessage);

    public DecodeData registUser(CM_UserMessage userMessage);

    public DecodeData logout(CM_UserMessage userMessage);
}
