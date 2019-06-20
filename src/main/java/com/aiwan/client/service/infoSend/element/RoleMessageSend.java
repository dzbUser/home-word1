package com.aiwan.client.service.infoSend.element;

import com.aiwan.client.LoginUser;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.client.util.Verification;
import com.aiwan.server.scenes.protocol.CM_Move;
import com.aiwan.server.scenes.protocol.CM_Shift;
import com.aiwan.server.user.account.protocol.CM_CreateRole;
import com.aiwan.server.user.role.equipment.protocol.CM_UnloadingEquipment;
import com.aiwan.server.user.role.equipment.protocol.CM_ViewEquipBar;
import com.aiwan.server.user.role.player.protocol.CM_RoleMessage;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;

/**
 * 角色系统发送协议枚举类
 * @author dengzebiao
 * @since 2019.
 * */
public enum  RoleMessageSend {

    /** 角色信息发送 */
    CREATE(1){
        @Override
        public void messageSend(String message){
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)){
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }
            String[] messages = message.split(" ");
            int job = Integer.parseInt(messages[0]);
            int sex = Integer.parseInt(messages[1]);
            //获取性别和职业
            CM_CreateRole cm_createRole = new CM_CreateRole();
            cm_createRole.setAccountId(LoginUser.getUsername());
            cm_createRole.setJob(job);
            cm_createRole.setSex(sex);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.CREATEROLE,cm_createRole));
        }

        @Override
        public boolean verify(String message){
            String[] messages = message.split(" ");
            if (messages.length != 2|| !Verification.canParseInt(messages[0])||!Verification.canParseInt(messages[1])){
                return false;
            }
            int job = Integer.parseInt(messages[0]);
            int sex = Integer.parseInt(messages[1]);
            if (job<0||job>1||sex<0||sex>1){
                return false;
            }
            return true;
        }
    },

    /** 角色移动 */
    MOVE(2){
        @Override
        public void messageSend(String message){

            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)){
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }
            //解析字符串含义
            String[] messages = message.split(" ");
            int x = Integer.parseInt(messages[0]);
            int y = Integer.parseInt(messages[1]);
            CM_Move move = new CM_Move();
            move.setCurrentX(LoginUser.getCurrentX());
            move.setCurrentY(LoginUser.getCurrentY());
            move.setTargetX(x);
            move.setTargetY(y);
            move.setUsername(LoginUser.getUsername());
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.MOVE,move));
        }

        @Override
        public boolean verify(String message){
            String[] messages = message.split(" ");
            if (messages.length != 2|| !Verification.canParseInt(messages[0])||!Verification.canParseInt(messages[1])){
                return false;
            }
            return true;
        }
    },

    /** 角色地图跳转 */
    SHIFT(3){
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)){
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }
            //解析字符串含义
            int map = Integer.parseInt(message);
            CM_Shift cm_shift = new CM_Shift();
            cm_shift.setMap(map);
            cm_shift.setUsername(LoginUser.getUsername());
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.SHIFT,cm_shift));
        }

        @Override
        public boolean verify(String message){
            if (!Verification.canParseInt(message)){
                return false;
            }
            return true;
        }
    },
    /** 查看用户信息 */
    VIEW_ROLE(4){
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)){
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }

            CM_RoleMessage cm_roleMessage = new CM_RoleMessage();
            cm_roleMessage.setAccountId(LoginUser.getUsername());
            cm_roleMessage.setrId(LoginUser.getRoles().get(0));
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.GETROLEMESSAGE,cm_roleMessage));
        }

    },
    /** 查看装备信息 */
    VIEW_EQUIP(5){
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)){
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }
            CM_ViewEquipBar cm_viewEquipBar = new CM_ViewEquipBar();
            cm_viewEquipBar.setAccountId(LoginUser.getUsername());
            cm_viewEquipBar.setrId(LoginUser.getRoles().get(0));
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.VIEWQEUIP,cm_viewEquipBar));
        }

    },

    /**
     * 卸装备
     */
    UNLOAD_EQUIP(6) {
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)) {
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }
            //解析字符串含义
            int position = Integer.parseInt(message);
            CM_UnloadingEquipment cm_unloadingEquipment = CM_UnloadingEquipment.valueOf(LoginUser.getUsername(), LoginUser.getRoles().get(0), position);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.UNLOADEQUIP, cm_unloadingEquipment));
        }

        @Override
        public boolean verify(String message) {
            if (!Verification.canParseInt(message)) {
                return false;
            }
            return true;
        }
    }
    ;

    RoleMessageSend(int num){
        this.num = num;
    }
    /** 所属位置 */
    private int num;
    /** 发送协议 */
    public void messageSend(String message){

    }

    /** 获取对应发送类 */
    public static RoleMessageSend getRoleMessageSend(int num){
        for (RoleMessageSend roleMessageSend:values()){
            if (roleMessageSend.getNum() == num){
                return roleMessageSend;
            }
        }
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage("没有此功能选项");
        return null;
    }


    public int getNum() {
        return num;
    }

    public RoleMessageSend setNum(int num) {
        this.num = num;
        return this;
    }

    /** 验证指令是否符合格式 */
    public boolean verify(String message){

        return true;
    }

}
