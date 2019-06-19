package com.aiwan.client.service.infoReceive;

import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.ClientResourseManager;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.prop.resource.EquipmentResource;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.user.protocol.Item.PropInfo;
import com.aiwan.server.user.protocol.SM_PropList;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author dengzebiao
 * @since 2019.6.12
 * 背包信息接收类
 * */

@InfoReceiveObject
public class PackageInfoReceive {

    private final int EQUIP = 3;

    private static Logger logger = LoggerFactory.getLogger(PackageInfoReceive.class);

    /** 创建角色有接收信息 */
    @InfoReceiveMethod(status = StatusCode.VIEWPROPLIST)
    public void viewPackage(SM_PropList sm_propList){
        //输出道具
        printPropByList(sm_propList.getList());
    }

    /** 根据list输出道具 */
    public void printPropByList(List<PropInfo> list){
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        StringBuffer stringBuffer = new StringBuffer();
        for (PropInfo propInfo:list){
            PropsResource propsResource = GetBean.getPropsManager().getProps(propInfo.getId());
            stringBuffer.append(propsResource.toString()+" ");
            if (propsResource.getType() == EQUIP){
                //是装备
                EquipmentResource equipmentResource = GetBean.getPropsManager().getEquipment(propInfo.getId());
                stringBuffer.append("位置:"+ ClientResourseManager.getContent("equipPosition", equipmentResource.getPosition()) + equipmentResource.toString());
            }else {
                stringBuffer.append("数量:"+propInfo.getNum());
            }
            stringBuffer.append("\n");
        }
        gameInterface.printOtherMessage(stringBuffer.toString());
    }
}
