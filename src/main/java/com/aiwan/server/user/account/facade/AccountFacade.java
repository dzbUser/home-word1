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
        GetBean.getUserService().login(session, userMessage.getUsername(), userMessage.getPassword());
    }

    /**
     * 用户注册控制
     * */
    public void registerUser(CM_Registered userMessage, Session session){
        GetBean.getUserService().registerUser(session, userMessage.getUsername(), userMessage.getPassword(), userMessage.getHpassword());
    }

    /**
     * 用户注销
     * */
    public void logout(CM_Logout userMessage, Session session){
        GetBean.getUserService().logout(userMessage.getUsername(), session);
    }

    /**
     * 高级登录
     * */
    public void hLogin(CM_HLogin cm_hlogin, Session session){
        GetBean.getUserService().hLogin(cm_hlogin.getUsername(), cm_hlogin.getHpassword(), session);
    }


    /**
     * 创建角色
     */
    public void createRole(CM_CreateRole cm_createRole, Session session){
        GetBean.getUserService().createRole(cm_createRole.getAccountId(), cm_createRole.getJob(), cm_createRole.getSex(), session);
    }


}
