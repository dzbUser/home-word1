package com.aiwan.user.service;

import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.publicsystem.service.ChannelManager;
import com.aiwan.user.entity.User;
import com.aiwan.user.dao.UserDao;
import com.aiwan.user.protocol.CM_UserMessage;
import com.aiwan.user.protocol.SM_UserMessage;
import com.aiwan.scenes.MapReource.CityResource;
import com.aiwan.scenes.MapReource.FieldResource;
import com.aiwan.util.*;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 用户业务逻辑类
 * */
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

    //用户登录
    @Override
    public void login(CM_UserMessage userMessage, Channel channel) {
//        if (UserCache.userCache.get(userMessage.getUsername())!=null){
//            logger.debug("您已经登录过了");
//        }
        User user = userDao.getUserByUsernameAndPassword(userMessage);
        DecodeData decodeData;
        //账号或者密码错误
        if (user == null){
            logger.debug(userMessage.getUsername()+"登录失败");
            String msg = new String("账号或者密码错误");
            decodeData = SMToDecodeData.shift(ConsequenceCode.LOGINFAIL,msg);
        }
        else {
            logger.debug(userMessage.getUsername()+"用户登录成功");
            //加入缓存
            UserCache.userCache.put(user.getUsername(),user);
            //加入用户Channel
//            if (ChannelManager.getChannelByUsername(user.getUsername()) == null){
//
//            }
            ChannelManager.putChannel(user.getUsername(),channel);

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
            decodeData = SMToDecodeData.shift(ConsequenceCode.LOGINSUCCESS,sm_userMessage);
        }
        channel.writeAndFlush(decodeData);
    }

    //用户注册
    @Override
    public void registUser(CM_UserMessage userMessage,Channel channel) {
        User user = userDao.getUserByUsername(userMessage);
        DecodeData decodeData = new DecodeData();
        //错误输入
        if(userMessage == null){
            logger.debug("输入错误");
            String content = "抱歉，用户账号已被注册，请选择其他账号";
            decodeData = SMToDecodeData.shift(ConsequenceCode.REGISTDAIL,content);
            channel.writeAndFlush(decodeData);
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
            decodeData = SMToDecodeData.shift(ConsequenceCode.REGISTSUCCESS,content);
            channel.writeAndFlush(decodeData);
        }else {//账号已被注册
            logger.debug("用户已存在");
            String content = "抱歉，用户账号已被注册，请选择其他账号";
            decodeData = SMToDecodeData.shift(ConsequenceCode.REGISTDAIL,content);
            channel.writeAndFlush(decodeData);
        }
    }


    @Override
    //用户注销
    public void logout(CM_UserMessage userMessage) {
//        logger.debug(UserCache.userCache.get(userMessage.getUsername()).getUsername()+"hahaah");
        UserCache.userCache.remove(userMessage.getUsername());
//        logger.debug(UserCache.userCache.get(userMessage.getUsername())+"");
        logger.debug("注销成功！");
        String content = new String("注销用户成功！");
        DecodeData decodeData = SMToDecodeData.shift(ConsequenceCode.LOGOUTSUCCESS,content);
        Channel channel = ChannelManager.getChannelByUsername(userMessage.getUsername());
        ChannelManager.removeChannel(userMessage.getUsername());
        channel.writeAndFlush(decodeData);
    }


}
