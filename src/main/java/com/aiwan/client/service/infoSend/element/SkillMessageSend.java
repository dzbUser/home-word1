package com.aiwan.client.service.infoSend.element;

import com.aiwan.client.LoginUser;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.client.util.Verification;
import com.aiwan.server.user.role.skill.protocol.CM_LearnSkill;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;

/**
 * @author dengzebiao
 * @since 2019.7.1
 * 技能发生协议
 */
public enum SkillMessageSend {

    /**
     * 学习技能
     */
    LEARN(1) {
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)) {
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }

            int skillId = Integer.parseInt(message);
            CM_LearnSkill cm_learnSkill = CM_LearnSkill.valueOf(LoginUser.getRoles().get(0), skillId);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.LEAENSKILL, cm_learnSkill));
        }

        @Override
        public boolean verify(String message) {
            if (Verification.canParseInt(message)) {
                return false;
            }
            return true;
        }
    };

    SkillMessageSend(int num) {
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
    public static SkillMessageSend getMessageSend(int num) {
        for (SkillMessageSend skillMessageSend : values()) {
            if (skillMessageSend.getNum() == num) {
                return skillMessageSend;
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
