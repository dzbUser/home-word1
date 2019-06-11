package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.annotation.PropType;
import com.aiwan.server.prop.model.PropUseInterface;
import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.util.StatusCode;

/**
 * @author dengzebiao
 * @since 2019.6.11
 * 装备的使用
 * */
@PropType(type = 3)
public class EquipmentUser implements PropUseInterface {
    /** 装备的使用 */
    @Override
    public int propUse(String accountId, Long rId, int pId, Session session) {
        //获取道具类
        Props props = GetBean.getPropsManager().getProps(pId);
        //扣除道具
        int status = GetBean.getBackpackService().deductionProp(accountId,pId);
        if (status == 0){
            session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,"您背包中没有"+props.getName()));
            return 0;
        }
        //装备使用
        int id = GetBean.getRoleService().equip(accountId,rId,pId);
        //装备错误
        if (id == -1){
            session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,"您的等级未达到要求"));
            return 0;
        }
        if (id != 0){
            //获取旧的装备
            Props oldEquipment  = GetBean.getPropsManager().getProps(id);
            //旧的装备存到背包
            GetBean.getBackpackService().obtainProp(accountId,oldEquipment,1);
        }
        session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,props.getName()+"使用成功！"));
        return 1;
    }
}
