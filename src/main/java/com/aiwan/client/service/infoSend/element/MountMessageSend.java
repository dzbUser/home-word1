package com.aiwan.client.service.infoSend.element;

import com.aiwan.client.LoginUser;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.user.role.mount.protocol.CM_MountUpgrade;
import com.aiwan.server.user.role.mount.protocol.CM_ViewMount;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;

/**
 * @author dengzebiao
 * @since 2019.6.18
 * 坐骑系统功能枚举类
 * */
public enum  MountMessageSend {
    /** 查看坐骑信息 */
    VIEW_MESSAGE(1){
        @Override
        public void messageSend(String message){
            CM_ViewMount cm_viewMount = new CM_ViewMount();
            cm_viewMount.setAccountId(LoginUser.getAccountId());
            cm_viewMount.setrId(LoginUser.getRoles().get(0));
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.VIEWMOUNT,cm_viewMount));
        }


    },
    /** 坐骑提升 */
    MOUNT_UPGRADE(2){
        @Override
        public void messageSend(String message){
            CM_MountUpgrade cm_mountUpgrade = new CM_MountUpgrade();
            cm_mountUpgrade.setAccountId(LoginUser.getAccountId());
            cm_mountUpgrade.setrId(LoginUser.getRoles().get(0));
            cm_mountUpgrade.setResourceId(2);
            cm_mountUpgrade.setNum(1);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.MOUNTUPGRADE,cm_mountUpgrade));
        }
    }
    ;
    MountMessageSend(int num){
        this.num = num;
    }
    /** 所属位置 */
    private int num;
    /** 发送协议 */
    public void messageSend(String message){

    }

    /** 获取对应发送类 */
    public static MountMessageSend getMountMessageSend(int num){
        for (MountMessageSend mountMessageSend:values()){
            if (mountMessageSend.getNum() == num){
                return mountMessageSend;
            }
        }
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage("没有此功能选项");
        return null;
    }


    public int getNum() {
        return num;
    }

    public MountMessageSend setNum(int num) {
        this.num = num;
        return this;
    }

    /** 验证指令是否符合格式 */
    public boolean verify(String message){
        return true;
    }
}
