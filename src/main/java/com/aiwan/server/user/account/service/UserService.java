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
    void login(Session session, String accountId, String password);

    /**
     * 用户注册
     * */
    void registerUser(Session session, String accountId, String password, String hPassword);

    /**
     * 用户注销
     * */
    void logout(String accountId, Session session);

    /**
     * 高级登录
     * */
    void hLogin(String accountId, String hPassword, Session session);


    /**
     * 创建角色
     */
    void createRole(String accountId, int job, int sex, Session session);



    /** 删除人物储存 */
    void deleteSave(String accountId);


}
