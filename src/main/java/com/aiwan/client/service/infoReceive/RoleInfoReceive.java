package com.aiwan.client.service.infoReceive;

import com.aiwan.client.LoginUser;
import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.ClientResourseManager;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.prop.resource.EquipmentResource;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.equipment.protocol.SM_ViewEquip;
import com.aiwan.server.user.role.equipment.protocol.item.EquipInfo;
import com.aiwan.server.user.role.player.protocol.SM_CreateRole;
import com.aiwan.server.user.role.player.protocol.SM_RoleMessage;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;

import java.util.Map;

/**
 * 角色信息接收类
 * @author dengzebiao
 * @since 2019.6.11
 * */
@InfoReceiveObject
public class RoleInfoReceive {

    /** 创建角色有接收信息 */
    @InfoReceiveMethod(status = StatusCode.CREATEROLESUCESS)
    public void createRoleInfo(SM_CreateRole createRole){
        //角色创建成功
        LoginUser.setRoles(createRole.getRoles());
        GameInterface gameInterface = (GameInterface)InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage(createRole.getMessage());
        gameInterface.printMapMessage(createRole.getMapMessage());
    }

    /** 查看角色属性 */
    @InfoReceiveMethod(status = StatusCode.VIEWROLEMESSAGE)
    public void viewRoleMessage(SM_RoleMessage sm_roleMessage){
        //角色创建成功
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("职业:"+ ClientResourseManager.getContent("job",sm_roleMessage.getJob()));
        stringBuffer.append(" 性别:"+ ClientResourseManager.getContent("sex",sm_roleMessage.getSex())+"\n");
        stringBuffer.append("等级:"+ sm_roleMessage.getLevel());
        stringBuffer.append(" 经验:"+ sm_roleMessage.getExperience());
        stringBuffer.append(" 升级所需经验:"+ sm_roleMessage.getNeedExperience()+"\n");
        //输出属性
        for (Map.Entry<AttributeType, AttributeElement> elementEntry:sm_roleMessage.getMap().entrySet()){
            stringBuffer.append(elementEntry.getValue().toString()+"\n");
        }
        //输出到游戏界面
        GameInterface GameInterface = (GameInterface)InterfaceManager.getFrame("game");
        GameInterface.printOtherMessage(stringBuffer.toString());
    }

    /** 查看用户装备返回 */
    @InfoReceiveMethod(status = StatusCode.VIEWEQUIP)
    public void viewEquip(SM_ViewEquip sm_viewEquip){
        GameInterface gameInterface = (GameInterface)InterfaceManager.getFrame("game");
        StringBuffer stringBuffer = new StringBuffer();
        int i = 1;
        for (EquipInfo equipInfo:sm_viewEquip.getList()){
            if (equipInfo.getId() == 0){
                //无装备
                stringBuffer.append("位置" + i + ":" + ClientResourseManager.getContent("equipPosition", equipInfo.getPosition()) + " 装备:空\n");
            }else {
                //有装备
                PropsResource propsResource = GetBean.getPropsManager().getPropsResource(equipInfo.getId());
                EquipmentResource equipmentResource = GetBean.getPropsManager().getEquipment(equipInfo.getId());
                stringBuffer.append("位置" + equipInfo.getPosition() + ":" + ClientResourseManager.getContent("equipPosition", equipmentResource.getPosition()));
                stringBuffer.append(" 装备:"+ propsResource.getName());
                stringBuffer.append("\n属性添加:"+ equipmentResource.toString()+"\n");
            }
            i++;
        }
        gameInterface.printOtherMessage(stringBuffer.toString()+"\n");
    }
}
