package com.aiwan.client.service.inforeceive;

import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.scenes.protocol.SM_ViewAllUnitInMap;
import com.aiwan.server.scenes.protocol.UnitDetailMessage;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.util.StatusCode;

import java.util.List;
import java.util.Map;

/**
 * 战斗信息接收类
 *
 * @author dengzebiao
 * @since 2019.7.10
 */
@InfoReceiveObject
public class FightInfoReceive {

    /**
     * 查看所有战斗单位信息
     */
    @InfoReceiveMethod(status = StatusCode.VIEW_ALLUNIT_INMAP)
    public void getUnitDetail(SM_ViewAllUnitInMap sm_viewAllUnitInMap) {

        StringBuffer stringBuffer = new StringBuffer();
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        //获取地图资源
        List<UnitDetailMessage> list = sm_viewAllUnitInMap.getUnitDetailMessages();
        if (list == null) {
            gameInterface.printOtherMessage("该地图没有怪物");
        }
        for (UnitDetailMessage unitDetailMessage : list) {
            stringBuffer.append("唯一ID:" + unitDetailMessage.getId() + "\n");
            stringBuffer.append("名字:" + unitDetailMessage.getName());
            if (unitDetailMessage.isMonster()) {
                stringBuffer.append("(怪物)");
            } else {
                stringBuffer.append("(角色)");
            }
            stringBuffer.append(" 等级:" + unitDetailMessage.getLevel());
            stringBuffer.append(" 当前HP:" + unitDetailMessage.getHP());
            stringBuffer.append(" 当前MP:" + unitDetailMessage.getMP());
            stringBuffer.append(" 位置:" + "(" + unitDetailMessage.getX() + "," + unitDetailMessage.getY() + ")" + "\n");
            stringBuffer.append("属性: ");
            for (Map.Entry<AttributeType, AttributeElement> elementEntry : unitDetailMessage.getAttributes().entrySet()) {
                stringBuffer.append(elementEntry.getValue().toString() + "\n");
            }
            stringBuffer.append("\n\n");
        }
        gameInterface.printOtherMessage(stringBuffer.toString());
    }

}
