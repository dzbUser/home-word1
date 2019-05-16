package com.aiwan.role.service;

import com.aiwan.publicsystem.DecodeData;
import com.aiwan.role.entity.User;
import com.aiwan.role.dao.UserDao;
import com.aiwan.role.protocol.CM_UserMessage;
import com.aiwan.role.protocol.SM_UserMessage;
import com.aiwan.scenes.MapReource.CityResource;
import com.aiwan.scenes.MapReource.FieldResource;
import com.aiwan.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Scope("singleton")
@Service("userService")
public class UserServiceImpl implements UserService {
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
//        if (UserCache.userCache.get(userMessage.getUsername())!=null){
//            logger.debug("您已经登录过了");
//        }
        User user = userDao.getUserByUsernameAndPassword(userMessage);
        DecodeData decodeData;
        //账号或者密码错误
        if (user == null){
            logger.debug(userMessage.getUsername()+"登录失败");
            String msg = new String("账号或者密码错误");
            decodeData = DecodeDataShift.shift(Protocol.LOGINFAIL,msg);
        }
        else {
            logger.debug(userMessage.getUsername()+"用户登录成功");
            UserCache.userCache.put(user.getUsername(),user);
            SM_UserMessage sm_userMessage = new SM_UserMessage();
            sm_userMessage.setUsername(user.getUsername());
            sm_userMessage.setMap(user.getMap());
            sm_userMessage.setCurrentX(user.getCurrentX());
            sm_userMessage.setCurrentY(user.getCurrentY());
            if (user.getMap() == 1){
                sm_userMessage.setMapMessage(CityResource.MaptoMapMessage(user.getCurrentX(),user.getCurrentY()));
            }
            else if(user.getMap() == 2){
                sm_userMessage.setMapMessage(FieldResource.MaptoMapMessage(user.getCurrentX(),user.getCurrentY()));
            }
            decodeData = DecodeDataShift.shift(Protocol.LOGINSUCCESS,sm_userMessage);
        }
        return decodeData;
    }

    @Override
    public DecodeData registUser(CM_UserMessage userMessage) {
        User user = userDao.getUserByUsername(userMessage);
        DecodeData decodeData = new DecodeData();
        //错误输入
        if(userMessage == null){
            logger.debug("输入错误");
            String content = "抱歉，用户账号已被注册，请选择其他账号";
            decodeData = DecodeDataShift.shift(Protocol.REGISTDAIL,content);
            return decodeData;
        }
        //账号可用
        else if (user == null){
            logger.debug("注册新用户");
            User user1 = new User();
            user1.setUsername(userMessage.getUsername());
            user1.setPassword(userMessage.getPassword());
            user1.setMap(MapResourceProtocol.CITY);
            user1.setCurrentX(CityResource.ORINGINX);
            user1.setCurrentY(CityResource.ORINGINY);
            userDao.insert(user1);
            String content = "恭喜您，注册成功！";
            decodeData = DecodeDataShift.shift(Protocol.REGISTSUCCESS,content);
            return decodeData;
        }else {//账号已被注册
            logger.debug("用户已存在");
            String content = "抱歉，用户账号已被注册，请选择其他账号";
            decodeData = DecodeDataShift.shift(Protocol.REGISTDAIL,content);
            return decodeData;
        }
    }


    @Override
    //用户注销
    public DecodeData logout(CM_UserMessage userMessage) {
//        logger.debug(UserCache.userCache.get(userMessage.getUsername()).getUsername()+"hahaah");
        UserCache.userCache.remove(userMessage.getUsername());
//        logger.debug(UserCache.userCache.get(userMessage.getUsername())+"");
        logger.debug("注销成功！");
        String content = new String("注销用户成功！");
        DecodeData decodeData = DecodeDataShift.shift(Protocol.LOGOUTSUCCESS,content);
        return decodeData;
    }


}
