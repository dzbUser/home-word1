package com.aiwan.client.service.infoSend.element;

import com.aiwan.client.LoginUser;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.scenes.protocol.MonsterMessage;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.skill.resource.SkillResource;
import com.aiwan.server.util.GetBean;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public enum MonsterMessageSend {
    /**
     * 查看地图中所有怪物
     */
    VIEWALLMONSTER(0) {
        @Override
        public void messageSend(String message) {
            Map<Integer, SkillResource> map = Collections.unmodifiableMap(GetBean.getSkillManager().getSkillResourceMap());
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

    ;

    MonsterMessageSend(int num) {
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
    public static MonsterMessageSend getMonsterMessageSend(int num) {
        for (MonsterMessageSend MonsterMessageSend : values()) {
            if (MonsterMessageSend.getNum() == num) {
                return MonsterMessageSend;
            }
        }
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage("没有此功能选项");
        return null;
    }


    public int getNum() {
        return num;
    }

    public MonsterMessageSend setNum(int num) {
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
