package com.aiwan.client.service.infoSend.element;

import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.user.role.team.protocol.CM_CreateTeam;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;

/**
 * 组队协议发送类
 *
 * @author dengzebiao
 * @since 2019.7.17
 */
public enum TeamMessageSend {

    /**
     * 创建队伍
     */
    CREATE_TEAM(1) {
        @Override
        public void messageSend(String message) {
            CM_CreateTeam cm_createTeam = new CM_CreateTeam();
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.CREATE_TEAM, cm_createTeam));
        }
    };

    TeamMessageSend(int num) {
        this.num = num;
    }

    /**
     * 所属位置
     */
    private int num;

    /**
     * 发送协议
     */
    public void messageSend(String message) {

    }

    /**
     * 获取对应发送类
     */
    public static TeamMessageSend getMessageSend(int num) {
        for (TeamMessageSend teamMessageSend : values()) {
            if (teamMessageSend.getNum() == num) {
                return teamMessageSend;
            }
        }
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage("没有此功能选项");
        return null;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    /**
     * 验证指令是否符合格式
     */
    public boolean verify(String message) {
        return true;
    }
}
