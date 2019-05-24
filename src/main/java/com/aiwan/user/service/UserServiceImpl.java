package com.aiwan.user.service;

import com.aiwan.publicsystem.common.Session;
import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.publicsystem.service.SessionManager;
import com.aiwan.publicsystem.service.TheatpoolManager;
import com.aiwan.scenes.mapresource.MapResource;
import com.aiwan.scenes.protocol.CM_Move;
import com.aiwan.scenes.protocol.CM_Shift;
import com.aiwan.user.model.User;
//import com.aiwan.user.dao.UserDao;
import com.aiwan.user.protocol.*;
import com.aiwan.util.*;
import com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;

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
    private UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }



    //用户登录
    @Override
    public void login(CM_Login userMessage, Session session) {
        /*
        * 1.查看缓存是否存在用户，若存在则说明用户已在线
        * 2.查看账号密码是否正确，若不正确则返回登录错误
        * 3.登录成功，加入缓存 a.username-session的映射
        * */
        DecodeData decodeData;
        //~~~~~~~~~~第一步~``````
        Session session1 = SessionManager.getSessionByUsername(userMessage.getUsername());
        if (session1 !=null){
            logger.debug("您已经登录过了");
            decodeData = SMToDecodeData.shift(ConsequenceCode.LOGINFAIL,"用户已在线，若想顶替，请选择高级登录");
            session.getChannel().writeAndFlush(decodeData);
            return;
        }
        User user = userManager.getUserByAcountId(userMessage.getUsername());

        //账号或者密码错误~~~~~~~第二步~~~~~~~~~
        if (user == null||!user.getPassword().equals(userMessage.getPassword())){
            logger.debug(userMessage.getUsername()+"登录失败");
            String msg = new String("账号或者密码错误");
            decodeData = SMToDecodeData.shift(ConsequenceCode.LOGINFAIL,msg);
        }
        else {
            //~~~~~~~~~~~~第三步~~~~~~~~~~~~
            logger.debug(userMessage.getUsername()+"用户登录成功");
            //加入缓存
            session.setUser(user);
            SessionManager.putSessionByUsername(userMessage.getUsername(),session);

            //把用户添加到地图资源中
            MapResource mapResource = GetBean.getMapManager().getMapResource((int) user.getMap());
            mapResource.putUser(user.getAcountId(),user);

            SM_UserMessage sm_userMessage = new SM_UserMessage();
            sm_userMessage.setUsername(user.getAcountId());
            sm_userMessage.setMap(user.getMap());
            sm_userMessage.setCurrentX(user.getCurrentX());
            sm_userMessage.setCurrentY(user.getCurrentY());
            sm_userMessage.setMapMessage(GetBean.getMapManager().getMapResource((int) user.getMap()).getMapContent(user.getCurrentX(),user.getCurrentY()));
            decodeData = SMToDecodeData.shift(ConsequenceCode.LOGINSUCCESS,sm_userMessage);
        }
        session.getChannel().writeAndFlush(decodeData);
    }

    //用户注册
    @Override
    public void registUser(CM_Registered userMessage, Session session) {
        User user = userManager.getUserByAcountId(userMessage.getUsername());
        DecodeData decodeData = new DecodeData();
        //错误输入
        if(userMessage == null){
            logger.debug("输入错误");
            String content = "输入错误";
            decodeData = SMToDecodeData.shift(ConsequenceCode.REGISTDAIL,content);
            session.getChannel().writeAndFlush(decodeData);
        }
        //账号可用
        else if (user == null){
            logger.debug("注册新用户");
            userManager.register(userMessage.getUsername(),userMessage.getPassword(),userMessage.getHpassword(),ORINGINMAP,ORINGINX,ORINGINY);
            String content = "恭喜您，注册成功！";
            decodeData = SMToDecodeData.shift(ConsequenceCode.REGISTSUCCESS,content);
            session.getChannel().writeAndFlush(decodeData);
        }else {//账号已被注册
            logger.debug("用户已存在");
            String content = "抱歉，用户账号已被注册，请选择其他账号";
            decodeData = SMToDecodeData.shift(ConsequenceCode.REGISTDAIL,content);
            session.getChannel().writeAndFlush(decodeData);
        }
    }


    @Override
    //用户注销
    public void logout(CM_Logout userMessage,Session session) {
        /*
        * 1.删除缓存
        * 2.从地图资源中删除
        * */
        SessionManager.removeSessionByUsername(userMessage.getUsername());
        logger.debug("注销成功！");
        String content = new String("注销用户成功！");
        DecodeData decodeData = SMToDecodeData.shift(ConsequenceCode.LOGOUTSUCCESS,content);
        User user = userManager.getUserByAcountId(userMessage.getUsername());
        //把用户从地图资源中移除
        MapResource mapResource = GetBean.getMapManager().getMapResource(user.getMap());
        mapResource.removeUser(userMessage.getUsername());
        session.getChannel().writeAndFlush(decodeData);
    }

    /**
     * 用户需顶替已登录用户登录
     * */
    @Override
    public void hLogin(CM_HLogin cm_hlogin, Session session) {
        /*
         * 1.查看账号以及高级密码，若错误则返回错误
         * 2.顶替用户上线，更新缓存
         * */
        DecodeData decodeData;
        User user = userManager.getUserByAcountId(cm_hlogin.getUsername());
        //账号或者密码错误
        if (user == null||!user.getHpassword().equals(cm_hlogin.getHpassword())){
            String msg = new String("账号或者高级密码错误");
            decodeData = SMToDecodeData.shift(ConsequenceCode.LOGINFAIL,msg);
            session.getChannel().writeAndFlush(decodeData);
            return;
        }

        //发送顶替下线信息
        Session session1 = SessionManager.getSessionByUsername(cm_hlogin.getUsername());
        if (session1!= null){
            session1.getChannel().writeAndFlush(SMToDecodeData.shift((short) ConsequenceCode.INSIST,"您已被顶替下线！"));
        }


        //更改缓存
        session.setUser(user);
        SessionManager.putSessionByUsername(cm_hlogin.getUsername(),session);
        //把用户添加到地图资源中
        MapResource mapResource = GetBean.getMapManager().getMapResource((int) user.getMap());
        mapResource.putUser(user.getAcountId(),user);

        //设置和发送信息
        SM_UserMessage sm_userMessage = new SM_UserMessage();
        sm_userMessage.setUsername(user.getAcountId());
        sm_userMessage.setMap(user.getMap());
        sm_userMessage.setCurrentX(user.getCurrentX());
        sm_userMessage.setCurrentY(user.getCurrentY());
        sm_userMessage.setMapMessage(GetBean.getMapManager().getMapResource((int) user.getMap()).getMapContent(user.getCurrentX(),user.getCurrentY()));
        decodeData = SMToDecodeData.shift(ConsequenceCode.LOGINSUCCESS,sm_userMessage);
        session.getChannel().writeAndFlush(decodeData);
    }

    public void move(final CM_Move cm_move, final Session session) {
        User user = SessionManager.getSessionByUsername(cm_move.getUsername()).getUser();
        Object data = "移动失败！";
        short type = ConsequenceCode.MOVEFAIL;
        //判断是否登录
        if (user == null){
            data  = "您还未登录！";
            type = ConsequenceCode.MOVEFAIL;
            DecodeData decodeData = SMToDecodeData.shift(type,data);
            session.getChannel().writeAndFlush(data);
            return;
        }
        //把业务抛给场景线程池
        TheatpoolManager.start("scenes", new Runnable() {
            @Override
            public void run() {
                GetBean.getScenesService().move(session,cm_move);
            }
        });
    }

    /**
     * 用户移动
     * */
    public void shift(final CM_Shift cm_shift, final Session session){
        User user = SessionManager.getSessionByUsername(cm_shift.getUsername()).getUser();
        Object data = "跳转失败！";
        short type = ConsequenceCode.SHIFTFAIL;
        //判断是否登录
        if (user == null){
            data  = "您还未登录！";
            type = ConsequenceCode.SHIFTFAIL;
            DecodeData decodeData = SMToDecodeData.shift(type,data);
            session.getChannel().writeAndFlush(decodeData);
            return;
        }
        //把业务抛给场景线程池
        TheatpoolManager.start("scenes", new Runnable() {
            @Override
            public void run() {
                GetBean.getScenesService().shift(session,cm_shift);
            }
        });
    }

}
