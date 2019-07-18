package com.aiwan.client.service.infoSend.element;

import com.aiwan.client.LoginUser;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.client.util.Verification;
import com.aiwan.server.world.scenes.protocol.CM_ViewAllUnitInMap;
import com.aiwan.server.user.role.fight.protocol.CM_UserSkill;
import com.aiwan.server.user.role.fight.protocol.CM_ViewFightBuff;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;

public enum FightMessageSend {
    /**
     * 查看地图中所有战斗单位
     */
    VIEWALLUNIT(0) {
        @Override
        public void messageSend(String message) {
            CM_ViewAllUnitInMap cm_viewAllUnitInMap = CM_ViewAllUnitInMap.valueOf(LoginUser.getMap());
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.VIEW_ALLUNIT_INMAP, cm_viewAllUnitInMap));
        }
    },
    /**
     * 使用技能
     */
    USER_SKILL(1) {
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)) {
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }

            String[] messages = message.split(" ");
            int barPostion = Integer.parseInt(messages[0]);
            long targetId = Long.parseLong(messages[1]);
            CM_UserSkill cm_userSkill = CM_UserSkill.valueOf(LoginUser.getRoles().get(0), targetId, barPostion);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.USESKILL, cm_userSkill));
        }

        @Override
        public boolean verify(String message) {
            String[] messages = message.split(" ");
            if (messages.length != 2 || !Verification.canParseNum(messages[0]) || !Verification.canParseNum(messages[1])) {
                return false;
            }
            return true;
        }
    },
    VIEW_FIGHT_BUFF(2) {
        @Override
        public void messageSend(String message) {
            CM_ViewFightBuff cm_viewFightBuff = new CM_ViewFightBuff();
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.VIEW_FIGHTBUFF, cm_viewFightBuff));
        }
    }
    ;

    FightMessageSend(int num) {
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
    public static FightMessageSend getMonsterMessageSend(int num) {
        for (FightMessageSend FightMessageSend : values()) {
            if (FightMessageSend.getNum() == num) {
                return FightMessageSend;
            }
        }
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage("没有此功能选项");
        return null;
    }


    public int getNum() {
        return num;
    }

    public FightMessageSend setNum(int num) {
        this.num = num;
        return this;
    }

    /**
     * 验证指令是否符合格式
     */
    public boolean verify(String message) {
        return true;
    }
}
