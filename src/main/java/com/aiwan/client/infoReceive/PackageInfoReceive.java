package com.aiwan.client.infoReceive;

import com.aiwan.client.LoginUser;
import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.ClientReceiveMap;
import com.aiwan.server.prop.resource.Equipment;
import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.user.Item.PropInfo;
import com.aiwan.server.user.backpack.protocol.SM_Package;
import com.aiwan.server.user.role.player.protocol.SM_CreateRole;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.StatusCode;
import org.hibernate.type.IntegerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

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
    @InfoReceiveMethod(status = StatusCode.VIEWPACKAGE)
    public void viewPackage(SM_Package sm_package){
        //输出道具
        for (PropInfo propInfo:sm_package.getList()){
            Props props = GetBean.getPropsManager().getProps(propInfo.getId());
            System.out.print(props.toString()+" ");
            if (props.getType() == EQUIP){
                //是装备
                Equipment equipment = GetBean.getPropsManager().getEquipment(propInfo.getId());
                System.out.print(equipment.toString());
            }else {
                System.out.print("数量:"+propInfo.getNum());
            }
            System.out.println();
        }
    }
}
