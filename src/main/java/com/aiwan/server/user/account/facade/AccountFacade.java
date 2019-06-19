package com.aiwan.server.user.account.facade;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.account.protocol.*;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Controller;

/**
 * @author dengzebiao
 * @since 2019.6.19
 * 用户协议
 * */
@Controller
public class AccountFacade {
    /**
     * 用户登录控制
     * */
    public void login(CM_Login userMessage, Session session){
        GetBean.getUserService().login(session,userMessage);
    }

    /**
     * 用户注册控制
     * */
    public void registerUser(CM_Registered userMessage, Session session){
        GetBean.getUserService().registerUser(session,userMessage);
    }

    /**
     * 用户注销
     * */
    public void logout(CM_Logout userMessage, Session session){
        GetBean.getUserService().logout(userMessage,session);
    }

    /**
     * 高级登录
     * */
    public void hLogin(CM_HLogin cm_hlogin, Session session){
        GetBean.getUserService().hLogin(cm_hlogin,session);
    }


    /**
     * 创建角色
     */
    public void createRole(CM_CreateRole cm_createRole, Session session){
        GetBean.getUserService().createRole(cm_createRole,session);
    }


}
