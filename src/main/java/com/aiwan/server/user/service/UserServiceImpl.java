package com.aiwan.server.user.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.publicsystem.service.ThreadPoolManager;
import com.aiwan.server.role.player.protocol.CM_RoleMessage;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.protocol.CM_Move;
import com.aiwan.server.scenes.protocol.CM_Shift;
import com.aiwan.server.user.model.User;
import com.aiwan.server.user.protocol.*;
import com.aiwan.server.util.*;
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
        * 4.查看是否有角色，若无角色返回角色注册提醒
        * */
        DecodeData decodeData;
        //~~~~~~~~~~第一步~``````
        Session session1 = SessionManager.getSessionByUsername(userMessage.getUsername());
        if (session1 !=null){
            logger.debug("您已经登录过了");
            decodeData = SMToDecodeData.shift(StatusCode.LOGINFAIL,"用户已在线，若想顶替，请选择高级登录");
            session.getChannel().writeAndFlush(decodeData);
            return;
        }
        User user = userManager.getUserByAccountId(userMessage.getUsername());

        //账号或者密码错误~~~~~~~第二步~~~~~~~~~
        if (user == null||!user.getPassword().equals(userMessage.getPassword())){
            logger.debug(userMessage.getUsername()+"登录失败");
            String msg = new String("账号或者密码错误");
            decodeData = SMToDecodeData.shift(StatusCode.LOGINFAIL,msg);
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
            //~~~~~~~~~~~第四步~~~~~~~~~~~~~
            if (user.getRoleNum() == 0){
                sm_userMessage.setCreated(false);
                sm_userMessage.setOtherMessage("您还未创建角色，请创建您的角色");
            }
            sm_userMessage.setUsername(user.getAcountId());
            sm_userMessage.setMap(user.getMap());
            sm_userMessage.setCurrentX(user.getCurrentX());
            sm_userMessage.setCurrentY(user.getCurrentY());
            sm_userMessage.setMapMessage(GetBean.getMapManager().getMapResource((int) user.getMap()).getMapContent(user.getCurrentX(),user.getCurrentY()));
            sm_userMessage.setRoles(user.getUserBaseInfo().getRoles());
            decodeData = SMToDecodeData.shift(StatusCode.LOGINSUCCESS,sm_userMessage);
        }
        session.getChannel().writeAndFlush(decodeData);
    }

    //用户注册
    @Override
    public void registUser(CM_Registered userMessage, Session session) {
        User user = userManager.getUserByAccountId(userMessage.getUsername());
        DecodeData decodeData = new DecodeData();
        //错误输入
        if(userMessage == null){
            logger.debug("输入错误");
            String content = "输入错误";
            decodeData = SMToDecodeData.shift(StatusCode.REGISTDAIL,content);
            session.getChannel().writeAndFlush(decodeData);
        }
        //账号可用
        else if (user == null){
            logger.debug("注册新用户");
            userManager.register(userMessage.getUsername(),userMessage.getPassword(),userMessage.getHpassword(),ORINGINMAP,ORINGINX,ORINGINY,1);
            String content = "恭喜您，注册成功！";
            decodeData = SMToDecodeData.shift(StatusCode.REGISTSUCCESS,content);
            session.getChannel().writeAndFlush(decodeData);
        }else {//账号已被注册
            logger.debug("用户已存在");
            String content = "抱歉，用户账号已被注册，请选择其他账号";
            decodeData = SMToDecodeData.shift(StatusCode.REGISTDAIL,content);
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
        DecodeData decodeData = SMToDecodeData.shift(StatusCode.LOGOUTSUCCESS,content);
        User user = userManager.getUserByAccountId(userMessage.getUsername());
        //保存用户数据
        userManager.save(user);
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
        User user = userManager.getUserByAccountId(cm_hlogin.getUsername());
        //账号或者密码错误
        if (user == null||!user.getHpassword().equals(cm_hlogin.getHpassword())){
            String msg = new String("账号或者高级密码错误");
            decodeData = SMToDecodeData.shift(StatusCode.LOGINFAIL,msg);
            session.getChannel().writeAndFlush(decodeData);
            return;
        }

        //发送顶替下线信息
        Session session1 = SessionManager.getSessionByUsername(cm_hlogin.getUsername());
        if (session1!= null){
            session1.getChannel().writeAndFlush(SMToDecodeData.shift((short) StatusCode.INSIST,"您已被顶替下线！"));
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
        sm_userMessage.setRoles(user.getUserBaseInfo().getRoles());
        sm_userMessage.setMapMessage(GetBean.getMapManager().getMapResource((int) user.getMap()).getMapContent(user.getCurrentX(),user.getCurrentY()));
        decodeData = SMToDecodeData.shift(StatusCode.LOGINSUCCESS,sm_userMessage);
        session.getChannel().writeAndFlush(decodeData);
    }

    @Override
    public void move(final CM_Move cm_move, final Session session) {
        User user = SessionManager.getSessionByUsername(cm_move.getUsername()).getUser();
        Object data = "移动失败！";
        short type = StatusCode.MOVEFAIL;
        //判断是否登录
        if (user == null){
            data  = "您还未登录！";
            type = StatusCode.MOVEFAIL;
            DecodeData decodeData = SMToDecodeData.shift(type,data);
            session.getChannel().writeAndFlush(data);
            return;
        }
        //把业务抛给场景线程池
        ThreadPoolManager.start("scenes", new Runnable() {
            @Override
            public void run() {
                GetBean.getScenesService().move(session,cm_move);
            }
        });
    }

    /**
     * 用户移动
     * */
    @Override
    public void shift(final CM_Shift cm_shift, final Session session){
        User user = SessionManager.getSessionByUsername(cm_shift.getUsername()).getUser();
        Object data = "跳转失败！";
        short type = StatusCode.SHIFTFAIL;
        //判断是否登录
        if (user == null){
            data  = "您还未登录！";
            type = StatusCode.SHIFTFAIL;
            DecodeData decodeData = SMToDecodeData.shift(type,data);
            session.getChannel().writeAndFlush(decodeData);
            return;
        }
        //把业务抛给场景线程池
        ThreadPoolManager.start("scenes", new Runnable() {
            @Override
            public void run() {
                GetBean.getScenesService().shift(session,cm_shift);
            }
        });
    }

    /**
     * 查看个人信息
     * */
    public void userMessage(CM_UserMessage userMessage,Session session){
        User user = SessionManager.getSessionByUsername(userMessage.getUsername()).getUser();
        Object data = "获取个人信息失败";
        int type = StatusCode.SHIFTFAIL;
        //判断是否登录
        if (user == null){
            data  = "您还未登录！";
            type = StatusCode.SHIFTFAIL;
            DecodeData decodeData = SMToDecodeData.shift(type,data);
            session.getChannel().writeAndFlush(decodeData);
            return;
        }
        String mapContent = GetBean.getMapManager().getMapResource(user.getMap()).getMapContent(user.getCurrentX(),user.getCurrentY());
        type = StatusCode.GETMESSAGESUCCESS;
        SM_UserMessage sm_userMessage = new SM_UserMessage();
        sm_userMessage.setMapMessage(mapContent);
        sm_userMessage.setCurrentX(user.getCurrentX());
        sm_userMessage.setCurrentY(user.getCurrentY());
        sm_userMessage.setMap(user.getMap());
        DecodeData decodeData = SMToDecodeData.shift(type,sm_userMessage);
        session.getChannel().writeAndFlush(decodeData);
    }

    /**
     * 创建角色
     * */
    @Override
    public void createRole(final CM_CreateRole cm_createRole, final Session session){
        logger.debug(cm_createRole.getAccountId()+"创建角色");
        User user = session.getUser();
        if (user == null){//还未登录
            DecodeData decodeData = SMToDecodeData.shift(StatusCode.CREATEROLEFAIL,"您还未登录!");
            session.getChannel().writeAndFlush(decodeData);
        }else if(user.getUserBaseInfo().getRoles().size() >= user.getMaxRole()){//查看角色量
            DecodeData decodeData = SMToDecodeData.shift(StatusCode.CREATEROLEFAIL,"您角色数已上限！");
            session.getChannel().writeAndFlush(decodeData);
        }
        else {//转交给角色线程池
            ThreadPoolManager.start("role", new Runnable() {
                @Override
                public void run() {
                    GetBean.getRoleService().create(session,cm_createRole);
                }
            });
        }
    }

    @Override
    public void getRoleMessage(final CM_RoleMessage cm_roleMessage, final Session session) {
        logger.debug("获取角色信息");
        User user = session.getUser();
        if (user == null){//还未登录
            DecodeData decodeData = SMToDecodeData.shift(StatusCode.NOLOGIN,"您还未登录!");
            session.getChannel().writeAndFlush(decodeData);
        }
        else {//转交给角色线程池
            ThreadPoolManager.start("role", new Runnable() {
                @Override
                public void run() {
                    GetBean.getRoleService().getRoleMessage(session,cm_roleMessage);
                }
            });
        }
    }

}
