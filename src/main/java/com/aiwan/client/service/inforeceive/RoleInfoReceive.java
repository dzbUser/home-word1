package com.aiwan.client.service.inforeceive;

import com.aiwan.client.LoginUser;
import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.ClientResourceManager;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.attributes.model.ImmutableAttributeElement;
import com.aiwan.server.user.role.buff.protocol.BuffMessage;
import com.aiwan.server.user.role.buff.protocol.SM_ViewBuff;
import com.aiwan.server.user.role.buff.resource.BuffResource;
import com.aiwan.server.user.role.equipment.protocol.SM_ViewEquip;
import com.aiwan.server.user.role.equipment.protocol.item.EquipInfo;
import com.aiwan.server.user.role.player.protocol.SM_CreateRole;
import com.aiwan.server.user.role.player.protocol.SM_RoleMessage;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    }

    /** 查看角色属性 */
    @InfoReceiveMethod(status = StatusCode.VIEWROLEMESSAGE)
    public void viewRoleMessage(SM_RoleMessage sm_roleMessage){
        //角色创建成功
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("角色名:" + sm_roleMessage.getName());
        stringBuffer.append(" 职业:" + ClientResourceManager.getContent("job", sm_roleMessage.getJob()));
        stringBuffer.append(" 性别:" + ClientResourceManager.getContent("sex", sm_roleMessage.getSex()) + "\n");
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
            stringBuffer.append("[" + i + "] ");
            if (equipInfo.getId() == 0){
                //无装备
                stringBuffer.append(ClientResourceManager.getContent("equipPosition", equipInfo.getPosition()) + " 装备:空");
            }else {
                //有装备
                PropsResource propsResource = GetBean.getPropsManager().getPropsResource(equipInfo.getId());
                stringBuffer.append(ClientResourceManager.getContent("equipPosition", propsResource.getPosition()));
                stringBuffer.append("\n" + propsResource.toString());
            }
            stringBuffer.append("\n\n");
            i++;
        }
        gameInterface.printOtherMessage(stringBuffer.toString()+"\n");
    }

    /**
     * 接受buff查看返回
     */
    @InfoReceiveMethod(status = StatusCode.VIEW_BUFF)
    public void viewBuff(SM_ViewBuff sm_viewBuff) {
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        StringBuffer stringBuffer = new StringBuffer();
        // 格式化时间
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd hh:mm:ss");
        //获取buff列表
        List<BuffMessage> list = sm_viewBuff.getList();
        for (int i = 0; i < list.size(); i++) {
            //获取buff信息
            BuffMessage buffMessage = list.get(i);
            //获取buff资源
            BuffResource buffResource = GetBean.getBuffManager().getBuffResourceByBuffId(buffMessage.getBuffId());
            stringBuffer.append("[" + i + "] buffId:" + list.get(i).getBuffId());
            stringBuffer.append(" 结束时间:" + formatter.format(new Date(buffMessage.getOverTime())) + "\n");
            stringBuffer.append("属性: ");
            Map<AttributeType, AttributeElement> map = ImmutableAttributeElement.wrapper(buffResource.getAttributeMap());
            for (AttributeElement attributeElement : map.values()) {
                //输出属性加成
                stringBuffer.append(attributeElement.toString() + " ");
            }
        }
        gameInterface.printOtherMessage(stringBuffer.toString() + "\n\n");
    }
}
