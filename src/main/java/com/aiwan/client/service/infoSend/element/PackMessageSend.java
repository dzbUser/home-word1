package com.aiwan.client.service.infoSend.element;

import com.aiwan.client.LoginUser;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.client.util.Verification;
import com.aiwan.server.prop.protocol.CM_PropUse;
import com.aiwan.server.user.backpack.protocol.CM_ObtainProp;
import com.aiwan.server.user.backpack.protocol.CM_ViewBackpack;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;

/**
 * @author dengzebiao
 * @since 2019.6.18
 * 背包系统枚举类
 * */
public enum PackMessageSend {
    /** 添加道具 */
    ADD_PROP(1){
        @Override
        public void messageSend(String message){
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)){
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }

            String[] messages = message.split(" ");
            int id = Integer.parseInt(messages[0]);
            int num = Integer.parseInt(messages[1]);
            CM_ObtainProp cm_obtainProp = new CM_ObtainProp();
            cm_obtainProp.setAccountId(LoginUser.getUsername());
            cm_obtainProp.setId(id);
            cm_obtainProp.setNum(num);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.OBTAINPROP,cm_obtainProp));
        }

        @Override
        public boolean verify(String message){
            String[] messages = message.split(" ");
            if (messages.length!= 2|| !Verification.canParseInt(messages[0])||!Verification.canParseInt(messages[1])){
                return false;
            }
            return true;
        }
    },

    /** 查看背包 */
    VIEW_PACK(2){
        @Override
        public void messageSend(String message){
            CM_ViewBackpack cm_viewBackpack = new CM_ViewBackpack();
            cm_viewBackpack.setAccountId(LoginUser.getUsername());
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.VIEWBACKPACK,cm_viewBackpack));
        }
    },
    /** 使用道具*/
    PROP_USER(3){
        @Override
        public void messageSend(String message){
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)){
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }

            int id = Integer.parseInt(message);
            CM_PropUse cm_propUse = new CM_PropUse();
            cm_propUse.setAccountId(LoginUser.getUsername());
            cm_propUse.setpId(id);
            cm_propUse.setrId(LoginUser.getRoles().get(0));
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.PROPUSER,cm_propUse));
        }

        @Override
        public boolean verify(String message){
            if (!Verification.canParseInt(message)){
                return false;
            }
            return true;
        }
    }
    ;
    PackMessageSend(int num){
        this.num = num;
    }
    /** 所属位置 */
    private int num;
    /** 发送协议 */
    public void messageSend(String message){

    }

    /** 获取对应发送类 */
    public static PackMessageSend getPackMessageSend(int num){
        for (PackMessageSend packMessageSend:values()){
            if (packMessageSend.getNum() == num){
                return packMessageSend;
            }
        }
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage("没有此功能选项");
        return null;
    }


    public int getNum() {
        return num;
    }

    public PackMessageSend setNum(int num) {
        this.num = num;
        return this;
    }

    /** 验证指令是否符合格式 */
    public boolean verify(String message){
        return true;
    }
}
