package com.aiwan.client.service.infoSend.element;

import com.aiwan.client.LoginUser;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.client.util.Verification;
import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.scenes.protocol.MonsterMessage;
import com.aiwan.server.scenes.protocol.RoleMessage;
import com.aiwan.server.user.backpack.protocol.CM_DropProps;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.fight.protocol.CM_UserSkill;
import com.aiwan.server.user.role.skill.resource.SkillResource;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public enum FightMessageSend {
    /**
     * 查看地图中所有怪物
     */
    VIEWALLMONSTER(0) {
        @Override
        public void messageSend(String message) {
            StringBuffer stringBuffer = new StringBuffer();
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            //获取地图资源
            List<MonsterMessage> list = LoginUser.getMapMessage().getMonsterList();
            if (list == null) {
                gameInterface.printOtherMessage("该地图没有怪物");
            }
            for (MonsterMessage monsterMessage : list) {
                MonsterResource monsterResource = GetBean.getMonsterManager().getResourceById(monsterMessage.getResourceId());
                stringBuffer.append("唯一ID:" + monsterMessage.getObjectId() + "\n");
                stringBuffer.append("名字:" + monsterResource.getName());
                stringBuffer.append(" 当前血量:" + monsterMessage.getBlood());
                stringBuffer.append(" 位置:" + "(" + monsterMessage.getPosition().getX() + "," + monsterMessage.getPosition().getY() + ")");
                stringBuffer.append(" 等级:" + monsterResource.getLevel() + "\n");
                stringBuffer.append("属性:");
                //输出属性
                for (Map.Entry<AttributeType, AttributeElement> elementEntry : monsterResource.getAttributeMap().entrySet()) {
                    stringBuffer.append(elementEntry.getValue().toString() + " ");
                }
                stringBuffer.append("\n\n");
            }
            gameInterface.printOtherMessage(stringBuffer.toString());
        }
    },
    /**
     * 查看地图中所有角色
     */
    VIEWALLROLE(1) {
        @Override
        public void messageSend(String message) {
            StringBuffer stringBuffer = new StringBuffer();
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            //获取地图资源
            List<RoleMessage> list = LoginUser.getMapMessage().getRoleList();
            if (list == null) {
                gameInterface.printOtherMessage("该地图没有怪物");
            }
            for (RoleMessage roleMessage : list) {
                stringBuffer.append("唯一ID:" + roleMessage.getrId() + "\n");
                stringBuffer.append("名字:" + roleMessage.getName());
                stringBuffer.append(" 角色等级:" + roleMessage.getLevel());
                stringBuffer.append(" 当前血量:" + roleMessage.getHP());
                stringBuffer.append(" 位置:" + "(" + roleMessage.getX() + "," + roleMessage.getY() + ")");
                stringBuffer.append("\n\n");
            }
            gameInterface.printOtherMessage(stringBuffer.toString());
        }
    },

    USER_SKILL(2) {
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
            if (messages.length != 2 || !Verification.canParseInt(messages[0]) || !Verification.canParseInt(messages[1])) {
                return false;
            }
            return true;
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
