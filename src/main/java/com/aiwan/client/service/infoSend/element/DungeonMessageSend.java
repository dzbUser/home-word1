package com.aiwan.client.service.infoSend.element;

import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.client.util.Verification;
import com.aiwan.server.user.role.team.protocol.CM_KickOutTeam;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.world.dungeon.protocol.CM_CreateDungeon;

/**
 * 副本客户端协议发送类
 */
public enum DungeonMessageSend {

    CREATE_DUNGEON(0) {
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)) {
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }
            int mapId = Integer.parseInt(message);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.CREATE_DUNGEON, CM_CreateDungeon.vulueOf(mapId)));
        }

        @Override
        public boolean verify(String message) {
            if (!Verification.canParseNum(message)) {
                return false;
            }
            return true;
        }
    };

    DungeonMessageSend(int num) {
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
    public static DungeonMessageSend getMessageSend(int num) {
        for (DungeonMessageSend messageSend : values()) {
            if (messageSend.getNum() == num) {
                return messageSend;
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
