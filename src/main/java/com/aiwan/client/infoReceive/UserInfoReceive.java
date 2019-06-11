package com.aiwan.client.infoReceive;

import com.aiwan.client.LoginUser;
import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.server.user.account.protocol.SM_UserMessage;
import com.aiwan.server.util.ObjectToBytes;
import com.aiwan.server.util.StatusCode;

/**
 * 用户信息接收类
 * */
@InfoReceiveObject
public class UserInfoReceive {

    /** 用户信息接收方法 */
    @InfoReceiveMethod(status = StatusCode.LOGINSUCCESS)
    public void userMessage(SM_UserMessage userMessage){
        LoginUser.setUsername (userMessage.getUsername());
        LoginUser.setCurrentX(userMessage.getCurrentX());
        LoginUser.setCurrentY(userMessage.getCurrentY());
        LoginUser.setMap(userMessage.getMap());
        LoginUser.setMapMessage(userMessage.getMapMessage());
        if (!userMessage.isCreated()){
            System.out.println(userMessage.getOtherMessage());
        }else {
            LoginUser.setRoles(userMessage.getRoles());
            System.out.println(userMessage.getUsername()+"登录成功");
            System.out.println(userMessage.getMapMessage());
        }
    }
}
