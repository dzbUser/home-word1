package com.aiwan.server.user.account.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.player.protocol.CM_RoleMessage;
import com.aiwan.server.user.account.model.User;
import com.aiwan.server.user.account.protocol.*;
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
        SM_UserMessage sm_userMessage = new SM_UserMessage();
        //~~~~~~~~~~第一步~``````
        Session session1 = SessionManager.getSessionByUsername(userMessage.getUsername());
        if (session1 !=null){
            logger.debug("用户已在线，若想顶替，请选择高级登录");
            sm_userMessage.setStatus(false);
            sm_userMessage.setOtherMessage("用户已在线，若想顶替，请选择高级登录");
            decodeData = SMToDecodeData.shift(StatusCode.LOGIN,sm_userMessage);
            session.messageSend(decodeData);
            return;
        }
        User user = userManager.getUserByAccountId(userMessage.getUsername());

        //账号或者密码错误~~~~~~~第二步~~~~~~~~~
        if (user == null||!user.getPassword().equals(userMessage.getPassword())){
            logger.debug(userMessage.getUsername()+"登录失败");
            sm_userMessage.setStatus(false);
            sm_userMessage.setOtherMessage("账号或者密码错误");
            decodeData = SMToDecodeData.shift(StatusCode.LOGIN,sm_userMessage);
        }
        else {
            //~~~~~~~~~~~~第三步~~~~~~~~~~~~
            logger.debug(userMessage.getUsername()+"用户登录成功");

            //加入缓存
            session.setUser(user);
            SessionManager.putSessionByUsername(userMessage.getUsername(),session);

            //把用户添加到地图资源中
            GetBean.getMapManager().putUser(user);


            //~~~~~~~~~~~第四步~~~~~~~~~~~~~
            if (user.getRoleNum() == 0){
                sm_userMessage.setCreated(false);
                sm_userMessage.setOtherMessage("您还未创建角色，请创建您的角色");
            }else {
                GetBean.getMapManager().sendMessageToUsers(user.getMap());
            }
            sm_userMessage.setUsername(user.getAcountId());
            sm_userMessage.setMap(user.getMap());
            sm_userMessage.setCurrentX(user.getCurrentX());
            sm_userMessage.setCurrentY(user.getCurrentY());
            sm_userMessage.setStatus(true);
            sm_userMessage.setMapMessage(GetBean.getMapManager().getMapContent(user.getCurrentX(),user.getCurrentY(),user.getMap()));
            sm_userMessage.setRoles(user.getUserBaseInfo().getRoles());
            decodeData = SMToDecodeData.shift(StatusCode.LOGIN,sm_userMessage);
            //给其他玩家发送信息
        }
        session.messageSend(decodeData);
    }

    /**用户注册*/
    @Override
    public void registerUser(CM_Registered userMessage, Session session) {
        User user = userManager.getUserByAccountId(userMessage.getUsername());
        //错误输入
        if(userMessage == null){
            logger.debug("输入错误");
            session.messageSend(SMToDecodeData.shift(StatusCode.REGISTER,SM_Register.valueOf(0,userMessage.getUsername())));
        }
        //账号可用
        else if (user == null){
            logger.debug("注册新用户");
            userManager.register(userMessage.getUsername(),userMessage.getPassword(),userMessage.getHpassword(),ORINGINMAP,ORINGINX,ORINGINY,1);
            GetBean.getBackpackService().createBackpack(userMessage.getUsername());
            session.messageSend(SMToDecodeData.shift(StatusCode.REGISTER,SM_Register.valueOf(1,userMessage.getUsername())));
        }else {//账号已被注册
            logger.debug("用户已存在");
            session.messageSend(SMToDecodeData.shift(StatusCode.REGISTER,SM_Register.valueOf(0,userMessage.getUsername())));
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
        GetBean.getMapManager().removeUser(user.getMap(),user.getAcountId());
        //session移除用户信息
        session.setUser(null);
        session.messageSend(decodeData);
        //给其余玩家发送信息
        GetBean.getMapManager().sendMessageToUsers(user.getMap());
    }

    /**
     * 用户需顶替已登录用户登录
     * */
    @Override
    public void hLogin(CM_HLogin cm_hlogin, Session session) {
        /*
         * 1.查看账号以及高级密码，若错误则返回错误
         * 2.顶替用户上线，更新缓存
         * 3.查看是否创建新角色，若创建则加入人物属性映射
         * */
        DecodeData decodeData;
        User user = userManager.getUserByAccountId(cm_hlogin.getUsername());
        //账号或者密码错误
        if (user == null||!user.getHpassword().equals(cm_hlogin.getHpassword())){
            String msg = new String("账号或者高级密码错误");
            decodeData = SMToDecodeData.shift(StatusCode.LOGINFAIL,msg);
            session.messageSend(decodeData);
            return;
        }

        //发送顶替下线信息
        Session session1 = SessionManager.getSessionByUsername(cm_hlogin.getUsername());
        if (session1!= null){
            session1.messageSend(SMToDecodeData.shift((short) StatusCode.INSIST,"您已被顶替下线！"));
        }


        //更改缓存
        session.setUser(user);
        SessionManager.putSessionByUsername(cm_hlogin.getUsername(),session);
        //把用户添加到地图资源中
        GetBean.getMapManager().putUser(user);

        //设置和发送信息
        SM_UserMessage sm_userMessage = new SM_UserMessage();

        //是否创建新角色
        if (user.getRoleNum() == 0){
            sm_userMessage.setCreated(false);
            sm_userMessage.setOtherMessage("您还未创建角色，请创建您的角色");
        }else{
            GetBean.getMapManager().sendMessageToUsers(user.getMap());
        }

        sm_userMessage.setUsername(user.getAcountId());
        sm_userMessage.setMap(user.getMap());
        sm_userMessage.setCurrentX(user.getCurrentX());
        sm_userMessage.setCurrentY(user.getCurrentY());
        sm_userMessage.setRoles(user.getUserBaseInfo().getRoles());
        sm_userMessage.setMapMessage(GetBean.getMapManager().getMapContent(user.getCurrentX(),user.getCurrentY(),user.getMap()));
        decodeData = SMToDecodeData.shift(StatusCode.LOGIN,sm_userMessage);
        session.messageSend(decodeData);
        //给其他玩家发送信息

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
            session.messageSend(decodeData);
            return;
        }
        String mapContent = GetBean.getMapManager().getMapContent(user.getCurrentX(),user.getCurrentY(),user.getMap());
        DecodeData decodeData = SMToDecodeData.shift(StatusCode.MESSAGE,mapContent);
        session.messageSend(decodeData);
    }

    /**
     * 创建角色
     * */
    @Override
    public void createRole(final CM_CreateRole cm_createRole, final Session session){
        logger.debug(cm_createRole.getAccountId()+"创建角色");
        User user = session.getUser();
        if(user.getUserBaseInfo().getRoles().size() >= user.getMaxRole()){
            //查看角色量
            DecodeData decodeData = SMToDecodeData.shift(StatusCode.MESSAGE,"您角色数已上限！");
            session.messageSend(decodeData);
        }
        else {
            //转交给角色业务
            GetBean.getRoleService().create(session,cm_createRole);
        }
    }

    @Override
    public void getRoleMessage(final CM_RoleMessage cm_roleMessage, final Session session) {
        logger.debug("获取角色信息"+cm_roleMessage.getAccountId());
        //转交给角色业务
        GetBean.getRoleService().getRoleMessage(session,cm_roleMessage);
    }



    @Override
    public void deleteSave(String accountId) {
        //删除人物session缓存
        SessionManager.removeSessionByUsername(accountId);
        logger.debug("删除人物缓存");
        User user = userManager.getUserByAccountId(accountId);
        //保存用户数据
        userManager.save(user);
        //把用户从地图资源中移除
        GetBean.getMapManager().removeUser(user.getMap(),user.getAcountId());
        //给他玩家发送信息
        GetBean.getMapManager().sendMessageToUsers(user.getMap());
    }

}
