package com.aiwan.server.user.account.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.player.protocol.CM_RoleMessage;
import com.aiwan.server.user.account.protocol.*;

/**
 * @author dengzebiao
 * 用户业务逻辑类
 * */
public interface UserService {

    /**
     * 用户登录
     * */
    void login(CM_Login userMessage, Session session);

    /**
     * 用户注册
     * */
    void registerUser(CM_Registered userMessage, Session session);

    /**
     * 用户注销
     * */
    void logout(CM_Logout userMessage, Session session);

    /**
     * 高级登录
     * */
    void hLogin(CM_HLogin cm_hlogin,Session session);


    /**
     * 创建角色
     */
    void createRole(CM_CreateRole cm_createRole,Session session);



    /** 删除人物储存 */
    void deleteSave(String accountId);


}
