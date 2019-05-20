package com.aiwan.user.service;

import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.publicsystem.service.ChannelManager;
import com.aiwan.scenes.mapresource.MapResource;
import com.aiwan.user.entity.User;
import com.aiwan.user.dao.UserDao;
import com.aiwan.user.protocol.*;
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
    //新用户初始地址
    private final static int ORINGINMAP = 1;
    //新用户初始坐标
    private final static int ORINGINX = 1;
    private final static int ORINGINY = 3;
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
    public void login(CM_Login userMessage, Channel channel) {
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

            //把用户添加到地图资源中
            MapResource mapResource = GetBean.getMapManager().getMapResource((int) user.getMap());
            mapResource.putUser(user.getUsername(),user);

            SM_UserMessage sm_userMessage = new SM_UserMessage();
            sm_userMessage.setUsername(user.getUsername());
            sm_userMessage.setMap(user.getMap());
            sm_userMessage.setCurrentX(user.getCurrentX());
            sm_userMessage.setCurrentY(user.getCurrentY());
            sm_userMessage.setMapMessage(GetBean.getMapManager().getMapResource((int) user.getMap()).getMapContent(user.getCurrentX(),user.getCurrentY()));
            decodeData = SMToDecodeData.shift(ConsequenceCode.LOGINSUCCESS,sm_userMessage);
        }
        channel.writeAndFlush(decodeData);
    }

    //用户注册
    @Override
    public void registUser(CM_Registered userMessage, Channel channel) {
        User user = userDao.getUserByUsername(userMessage.getUsername());
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
            user1.setMap(ORINGINMAP);
            user1.setCurrentX(ORINGINX);
            user1.setCurrentY(ORINGINY);
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
    public void logout(CM_Logout userMessage,Channel channel) {
//        logger.debug(UserCache.userCache.get(userMessage.getUsername()).getUsername()+"hahaah");
        UserCache.userCache.remove(userMessage.getUsername());
//        logger.debug(UserCache.userCache.get(userMessage.getUsername())+"");
        logger.debug("注销成功！");
        String content = new String("注销用户成功！");
        DecodeData decodeData = SMToDecodeData.shift(ConsequenceCode.LOGOUTSUCCESS,content);
        ChannelManager.removeChannel(userMessage.getUsername());
        User user = userDao.getUserByUsername(userMessage.getUsername());
        //把用户从地图资源中移除
        MapResource mapResource = GetBean.getMapManager().getMapResource(user.getMap());
        mapResource.removeUser(userMessage.getUsername());
        if(channel != null){
            channel.writeAndFlush(decodeData);
        }
    }


}
