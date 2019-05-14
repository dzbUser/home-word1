package com.aiwan.service;

import com.aiwan.model.User;
import com.aiwan.publicsystem.CM_UserMessage;
import com.aiwan.publicsystem.DecodeData;

public interface UserService {
    public User getUserByUid(int uid);

    public DecodeData login(CM_UserMessage userMessage);
}
