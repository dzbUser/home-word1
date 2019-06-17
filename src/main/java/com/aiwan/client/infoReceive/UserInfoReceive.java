package com.aiwan.client.infoReceive;

import com.aiwan.client.LoginUser;
import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.server.scenes.protocol.SM_Move;
import com.aiwan.server.scenes.protocol.SM_Shift;
import com.aiwan.server.user.account.protocol.SM_Register;
import com.aiwan.server.user.account.protocol.SM_UserMessage;
import com.aiwan.server.util.StatusCode;


/**
 * 用户信息接收类
 * */
@InfoReceiveObject
public class UserInfoReceive {

    /** 用户信息接收方法 */
    @InfoReceiveMethod(status = StatusCode.LOGIN)
    public void userMessage(SM_UserMessage userMessage){
        if (!userMessage.isStatus()){
            //登录失败
            System.out.println(userMessage.getOtherMessage());
            return;
        }
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
        }
    }

    /** 发送提示信息 */
    @InfoReceiveMethod(status = StatusCode.MESSAGE)
    public void sendMessage(String message){
        System.out.println(message);
    }

    /** 注销 */
    @InfoReceiveMethod(status = StatusCode.LOGOUTSUCCESS)
    public void logout(String message){
        System.out.println(message);
        LoginUser.setUsername("");
        LoginUser.setRoles(null);
    }

    /** 用户移动接收信息 */
    @InfoReceiveMethod(status = StatusCode.MOVESUCCESS)
    public void move(SM_Move sm_move){
        LoginUser.setCurrentY(sm_move.getTargetY());
        LoginUser.setCurrentX(sm_move.getTargetX());
        if (sm_move.getStatus() == 1){
            System.out.println("移动成功");
        }
    }

    /** 地图跳转 */
    @InfoReceiveMethod(status = StatusCode.SHIFTSUCCESS)
    public void shift(SM_Shift sm_shift){
        LoginUser.setCurrentY(sm_shift.getTargetY());
        LoginUser.setCurrentX(sm_shift.getTargetX());
        LoginUser.setMap(sm_shift.getMap());
        LoginUser.setMapMessage(sm_shift.getMapMessage());
    }

    /** 被顶号 */
    @InfoReceiveMethod(status = StatusCode.INSIST)
    public void insit(String message){
        logout(message);
    }

    /** 注册*/
    @InfoReceiveMethod(status = StatusCode.REGISTER)
    public void register(SM_Register sm_register){
        if (sm_register.getStatus() == 1){
            //注册成功
            System.out.println(sm_register.getAccountId()+"注册成功");
        }else {
            System.out.println(sm_register.getAccountId()+"注册失败");
        }
    }

    /** 接收地图信息 */
    @InfoReceiveMethod(status = StatusCode.MAPMESSAGE)
    public void getMapMessage(String message){
        System.out.println(message);
    }
}
