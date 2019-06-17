package com.aiwan.client.service.infoReceive;

import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.ClientResourseManager;
import com.aiwan.server.prop.resource.Equipment;
import com.aiwan.server.prop.resource.Props;
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
        for (PropInfo propInfo:list){
            Props props = GetBean.getPropsManager().getProps(propInfo.getId());
            System.out.print(props.toString()+" ");
            if (props.getType() == EQUIP){
                //是装备
                Equipment equipment = GetBean.getPropsManager().getEquipment(propInfo.getId());
                System.out.print("位置:"+ ClientResourseManager.getContent("equipPosition",equipment.getPosition()) +equipment.toString());
            }else {
                System.out.print("数量:"+propInfo.getNum());
            }
            System.out.println();
        }
    }
}
