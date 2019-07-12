package com.aiwan.client.service.infoSend.element;

import com.aiwan.client.LoginUser;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.client.util.Verification;
import com.aiwan.server.user.role.skill.protocol.*;

import com.aiwan.server.user.role.skill.resource.SkillLevelResource;
import com.aiwan.server.user.role.skill.resource.SkillResource;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;

import java.util.Collections;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.7.1
 * 技能发生协议
 */
public enum SkillMessageSend {
    /**
     * 查看所有技能
     */
    VIEWALLSKILL(0) {
        @Override
        public void messageSend(String message) {
            Map<Integer, SkillResource> map = Collections.unmodifiableMap(GetBean.getSkillManager().getSkillResourceMap());
            StringBuffer stringBuffer = new StringBuffer();
            //输出道具
            for (SkillResource skillElement : map.values()) {
                SkillResource skillResource = GetBean.getSkillManager().getSkillResourceBySkillId(skillElement.getSkillId());
                stringBuffer.append("[" + skillResource.getSkillId() + "]" + "名字:" + skillResource.getSkillName() + " 等级:" + 1);
                SkillLevelResource skillLevelResource = GetBean.getSkillManager().getSkillLevelReById(skillElement.getSkillId(), 1);
                stringBuffer.append(" 伤害:" + skillLevelResource.getSkillAttack() / 100 + "%");
                stringBuffer.append(" 学习等级要求:" + GetBean.getSkillManager().getSkillLevelReById(skillElement.getSkillId(), 1).getRoleLevelDemand() + "\n");
                stringBuffer.append("目标数:" + skillLevelResource.getNum());
                if (1 < GetBean.getSkillManager().getMaxLevel(skillElement.getSkillId())) {
                    skillLevelResource = GetBean.getSkillManager().getSkillLevelReById(skillElement.getSkillId(), (2));
                    stringBuffer.append(" 升级等级要求：" + skillLevelResource.getRoleLevelDemand() + "\n\n");
                } else {
                    stringBuffer.append(" 技能达到最高等级\n\n");
                }
            }
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            gameInterface.printOtherMessage(stringBuffer.toString());
        }
    },
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
            if (!Verification.canParseInt(message)) {
                return false;
            }
            return true;
        }
    },
    /**
     * 查看所学技能
     */
    VIEWLEARNEDSKILL(2) {
        @Override
        public void messageSend(String message) {
            CM_ViewLearnSkill cm_viewLearnSkill = CM_ViewLearnSkill.valueOf(LoginUser.getRoles().get(0));
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.VIEWLEARNSKILL, cm_viewLearnSkill));
        }

    },
    /**
     * 技能升级
     */
    UPGRADE(3) {
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)) {
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }

            int skillId = Integer.parseInt(message);
            CM_UpgradeSkill cm_upgradeSkill = CM_UpgradeSkill.valueOf(LoginUser.getRoles().get(0), skillId);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.UPGRADESKILL, cm_upgradeSkill));
        }

        @Override
        public boolean verify(String message) {
            if (!Verification.canParseInt(message)) {
                return false;
            }
            return true;
        }
    },
    /**
     * 技能移动
     */
    MOVE(4) {
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)) {
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }

            String[] messages = message.split(" ");
            int skillId = Integer.parseInt(messages[0]);
            int position = Integer.parseInt(messages[1]);
            CM_MoveSkill cm_moveSkill = CM_MoveSkill.valueOf(LoginUser.getRoles().get(0), skillId, position);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.MOVESKILL, cm_moveSkill));
        }

        @Override
        public boolean verify(String message) {
            String[] messages = message.split(" ");
            if (messages.length != 2 || !Verification.canParseInt(messages[0]) || !Verification.canParseInt(messages[1])) {
                return false;
            }
            return true;
        }
    },
    /**
     * 技能移动
     */
    VIEWBAR(5) {
        @Override
        public void messageSend(String message) {
            CM_ViewSkillBar cm_viewSkillBar = CM_ViewSkillBar.valueOf(LoginUser.getRoles().get(0));
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.VIEWSKILLBAR, cm_viewSkillBar));
        }

    },
    ;

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
