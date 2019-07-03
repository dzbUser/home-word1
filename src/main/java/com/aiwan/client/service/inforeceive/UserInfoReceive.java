package com.aiwan.client.service.inforeceive;

import com.aiwan.client.LoginUser;
import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.ClientResourceManager;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.publicsystem.protocol.SM_PromptMessage;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.protocol.RoleMessage;
import com.aiwan.server.scenes.protocol.SM_Move;
import com.aiwan.server.scenes.protocol.SM_RolesInMap;
import com.aiwan.server.scenes.protocol.SM_Shift;
import com.aiwan.server.user.account.protocol.SM_Register;
import com.aiwan.server.user.account.protocol.SM_UserMessage;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;
import org.omg.PortableInterceptor.LOCATION_FORWARD;

import javax.swing.*;
import java.util.List;
import java.util.Map;


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
    }

    /** 用户移动接收信息 */
    @InfoReceiveMethod(status = StatusCode.MOVESUCCESS)
    public void move(SM_Move sm_move){
        LoginUser.setCurrentY(sm_move.getTargetY());
        LoginUser.setCurrentX(sm_move.getTargetX());
        if (sm_move.getStatus() == 1){
            //输出到客户端
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            gameInterface.printOtherMessage("移动成功");
        }

    }

    /** 地图跳转 */
    @InfoReceiveMethod(status = StatusCode.SHIFTSUCCESS)
    public void shift(SM_Shift sm_shift){
        LoginUser.setCurrentY(sm_shift.getTargetY());
        LoginUser.setCurrentX(sm_shift.getTargetX());
        LoginUser.setMap(sm_shift.getMap());
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
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
    public void getMapMessage(SM_RolesInMap sm_rolesInMap) {
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");

        //加入备注
        LoginUser.setRoleMessages(sm_rolesInMap.getList());
        LoginUser.setMap(sm_rolesInMap.getMap());
        LoginUser.setCurrentX(sm_rolesInMap.getX());
        LoginUser.setCurrentY(sm_rolesInMap.getY());

        gameInterface.printMapMessage(getMapContent(sm_rolesInMap.getMap(), sm_rolesInMap.getX(), sm_rolesInMap.getY(), sm_rolesInMap.getList()));
    }

    /**
     * 获取地图信息
     */
    public String getMapContent(int mapType, int x, int y, List<RoleMessage> list) {
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

        for (RoleMessage roleMessage : list) {
            mapMessages[roleMessage.getX() - 1][roleMessage.getY() - 1] = roleMessage.getName();
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
