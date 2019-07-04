package com.aiwan.client.service.inforeceive;

import com.aiwan.client.LoginUser;
import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.ClientResourceManager;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.publicsystem.protocol.SM_PromptMessage;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.protocol.MonsterMessage;
import com.aiwan.server.scenes.protocol.RoleMessage;
import com.aiwan.server.scenes.protocol.SM_MapMessage;
import com.aiwan.server.user.account.protocol.SM_Register;
import com.aiwan.server.user.account.protocol.SM_UserMessage;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;

import javax.swing.*;
import java.util.List;


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
        LoginUser.setAccountId(userMessage.getAccountId());

        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");

        if (!userMessage.isCreated()){

            gameInterface.printOtherMessage(userMessage.getOtherMessage());
        }else {
            LoginUser.setRoles(userMessage.getRoles());
            gameInterface.printOtherMessage("登录成功");
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
    public void sendMessage(SM_PromptMessage sm_promptMessage) {
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage(sm_promptMessage.getOtherMessage() + ClientResourceManager.getContent("prompt", sm_promptMessage.getPromptCode()));
    }

    /** 注销 */
    @InfoReceiveMethod(status = StatusCode.LOGOUTSUCCESS)
    public void logout(String message){
        LoginUser.setAccountId("");
        LoginUser.setRoles(null);
        LoginUser.setMapMessage(null);
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
    public void getMapMessage(SM_MapMessage sm_MapMessage) {
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");

        //加入备注
        LoginUser.setMapMessage(sm_MapMessage);
        LoginUser.setMap(sm_MapMessage.getMap());
        LoginUser.setCurrentX(sm_MapMessage.getX());
        LoginUser.setCurrentY(sm_MapMessage.getY());

        gameInterface.printMapMessage(getMapContent(sm_MapMessage.getMap(), sm_MapMessage.getX(), sm_MapMessage.getY(), sm_MapMessage.getRoleList(), sm_MapMessage.getMonsterList()));
    }

    /**
     * 获取地图信息
     */
    public String getMapContent(int mapType, int x, int y, List<RoleMessage> roleList, List<MonsterMessage> monsterMessageList) {
        //获取地图静态资源
        MapResource mapResource = GetBean.getMapManager().getMapResource(mapType);
        StringBuilder stringBuilder = new StringBuilder();
        //添加地图资源
        stringBuilder.append("您所在位子为" + mapResource.getName() + "的(" + x + "," + y + ")\n");

        String[][] mapMessages = new String[mapResource.getWidth()][mapResource.getHeight()];
        for (int i = 0; i < mapResource.getWidth(); i++) {
            for (int j = 0; j < mapResource.getHeight(); j++) {
                mapMessages[i][j] = mapResource.getMapMessage()[i][j];
            }
        }

        for (RoleMessage roleMessage : roleList) {
            mapMessages[roleMessage.getX() - 1][roleMessage.getY() - 1] = roleMessage.getName();
        }

        for (MonsterMessage monsterMessage : monsterMessageList) {
            MonsterResource monsterResource = GetBean.getMonsterManager().getResourceById(monsterMessage.getResourceId());
            mapMessages[monsterMessage.getPosition().getX() - 1][monsterMessage.getPosition().getY() - 1] = monsterResource.getName();
        }

        for (int i = 0; i < mapResource.getWidth(); i++) {
            for (int j = 0; j < mapResource.getHeight(); j++) {
                stringBuilder.append(mapMessages[i][j] + " ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
