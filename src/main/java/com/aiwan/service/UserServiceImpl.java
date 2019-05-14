package com.aiwan.service;

import com.aiwan.dao.UserDao;
import com.aiwan.model.User;
import com.aiwan.netty.Encoder;
import com.aiwan.publicsystem.CM_UserMessage;
import com.aiwan.publicsystem.DecodeData;
import com.aiwan.util.DeepClone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    public User getUserByUid(int uid){
        User user = userDao.getUserById(uid);
        return user;
    }

    @Override
    public DecodeData login(CM_UserMessage userMessage) {
        User user = userDao.getUserByUsernameAndPassword(userMessage);
        DecodeData decodeData = new DecodeData();
        //账号或者密码错误
        if (user == null){
            logger.debug(userMessage.getUsername()+"登录失败");

            decodeData.setType((short) 5);
            String msg = "账号或者密码错误";
            byte[] data = msg.getBytes();
            decodeData.setLength(data.length);
            decodeData.setData(data);

        }
        else {
            logger.debug(userMessage.getUsername()+"用户登录成功");
            decodeData.setType((short)1);
            CM_UserMessage userMessage1 = new CM_UserMessage();
            userMessage1.setUsername(user.getUsername());
            userMessage1.setPassword(user.getPassword());
            byte[] data = DeepClone.writeInto(userMessage1);
            decodeData.setLength(data.length);
            decodeData.setData(data);
        }
        return decodeData;
    }
}
