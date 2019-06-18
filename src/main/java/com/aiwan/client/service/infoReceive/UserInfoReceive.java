package com.aiwan.client.service.infoReceive;

import com.aiwan.client.LoginUser;
import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.scenes.protocol.SM_Move;
import com.aiwan.server.scenes.protocol.SM_Shift;
import com.aiwan.server.user.account.protocol.SM_Register;
import com.aiwan.server.user.account.protocol.SM_UserMessage;
import com.aiwan.server.util.StatusCode;

import javax.swing.*;


/**
 * 用户信息接收类
 * */
@InfoReceiveObject
public class UserInfoReceive {

    /** 用户信息接收方法 */
    /** 用户登录消息接收 */
    @InfoReceiveMethod(status = StatusCode.LOGIN)
    public void userMessage(SM_UserMessage userMessage){
        if (!userMessage.isStatus()){
            //登录失败
            JOptionPane.showMessageDialog(new JPanel(), userMessage.getOtherMessage(), "标题",JOptionPane.WARNING_MESSAGE);
            return;
        }
        //设置登录缓存
        LoginUser.setUsername (userMessage.getUsername());
        LoginUser.setCurrentX(userMessage.getCurrentX());
        LoginUser.setCurrentY(userMessage.getCurrentY());
        LoginUser.setMap(userMessage.getMap());
        LoginUser.setMapMessage(userMessage.getMapMessage());
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");

        if (!userMessage.isCreated()){

            gameInterface.printOtherMessage(userMessage.getOtherMessage());
        }else {
            LoginUser.setRoles(userMessage.getRoles());
            gameInterface.printOtherMessage("登录成功");
            gameInterface.printMapMessage(userMessage.getMapMessage());
        }

        InterfaceManager.getFrame("game").setVisible(true);
        InterfaceManager.getFrame("heightLogin").setVisible(false);
        InterfaceManager.getFrame("login").setVisible(false);
    }

    /** 登录返回接收 */
    @InfoReceiveMethod(status = StatusCode.REGISTER)
    public void register(SM_Register sm_register){
        if (sm_register.getStatus() == 1){
            //注册成功
            JOptionPane.showMessageDialog(new JPanel(), sm_register.getAccountId()+"注册成功", "标题",JOptionPane.WARNING_MESSAGE);
            InterfaceManager.getFrame("register").setVisible(false);
            InterfaceManager.getFrame("login").setVisible(true);
        }else {
            JOptionPane.showMessageDialog(new JPanel(), sm_register.getAccountId()+"账号已存在", "标题",JOptionPane.WARNING_MESSAGE);
        }
    }


    /** 发送提示信息 */
    @InfoReceiveMethod(status = StatusCode.MESSAGE)
    public void sendMessage(String message){
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage(message);
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
            //输出到客户端
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            gameInterface.printMapMessage(sm_move.getMapMessage());
            gameInterface.printOtherMessage("移动成功");
        }

    }

    /** 地图跳转 */
    @InfoReceiveMethod(status = StatusCode.SHIFTSUCCESS)
    public void shift(SM_Shift sm_shift){
        LoginUser.setCurrentY(sm_shift.getTargetY());
        LoginUser.setCurrentX(sm_shift.getTargetX());
        LoginUser.setMap(sm_shift.getMap());
        LoginUser.setMapMessage(sm_shift.getMapMessage());
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printMapMessage(sm_shift.getMapMessage());
        gameInterface.printOtherMessage("跳转成功");
    }

    /** 被顶号 */
    @InfoReceiveMethod(status = StatusCode.INSIST)
    public void insit(String message){
        logout(message);
        JOptionPane.showMessageDialog(new JPanel(), "您已经被顶替下线", "标题",JOptionPane.WARNING_MESSAGE);
        InterfaceManager.getFrame("game").setVisible(false);
        InterfaceManager.getFrame("login").setVisible(true);
    }


    /** 接收地图信息 */
    @InfoReceiveMethod(status = StatusCode.MAPMESSAGE)
    public void getMapMessage(String message){
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printMapMessage(message);
    }
}
