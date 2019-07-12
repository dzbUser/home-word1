package com.aiwan.client.service.inforeceive;

import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.scenes.protocol.SM_ViewAllUnitInMap;
import com.aiwan.server.scenes.protocol.UnitDetailMessage;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.attributes.model.ImmutableAttributeElement;
import com.aiwan.server.user.role.buff.protocol.BuffMessage;
import com.aiwan.server.user.role.buff.resource.BuffResource;
import com.aiwan.server.user.role.buff.resource.EffectResource;
import com.aiwan.server.user.role.fight.protocol.FightBuffMessage;
import com.aiwan.server.user.role.fight.protocol.SM_ViewFightBuff;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    /**
     * 查看所有buff
     */
    @InfoReceiveMethod(status = StatusCode.VIEW_FIGHT_BUFF)
    public void viewFightBuff(SM_ViewFightBuff sm_viewFightBuff) {
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        StringBuffer stringBuffer = new StringBuffer();
        // 格式化时间
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd hh:mm:ss");
        //获取buff列表
        List<FightBuffMessage> list = sm_viewFightBuff.getList();
        for (int i = 0; i < list.size(); i++) {
            //获取buff信息
            FightBuffMessage buffMessage = list.get(i);
            //获取buff资源
            EffectResource effectResource = GetBean.getBuffManager().getEffectResource(buffMessage.getEffectId());
            stringBuffer.append("[" + i + "] effectId:" + list.get(i).getEffectId());
            stringBuffer.append(" 结束时间:" + formatter.format(new Date(buffMessage.getOverTime())));
            stringBuffer.append("说明:" + effectResource.getDec());
        }
        gameInterface.printOtherMessage(stringBuffer.toString() + "\n");
    }
}
