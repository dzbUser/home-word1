package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.annotation.PropType;
import com.aiwan.server.prop.model.PropUseInterface;
import com.aiwan.server.prop.resource.PropsResource;
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
public class EquipmentUse implements PropUseInterface {
    /** 装备的使用 */
    @Override
    public void propUse(String accountId, Long rId, int pId, Session session) {
        //获取道具类
        PropsResource propsResource = GetBean.getPropsManager().getProps(pId);
        //扣除道具
        int status = GetBean.getBackpackService().deductionProp(accountId,pId);
        if (status == 0){
            session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,"您背包中没有"+ propsResource.getName()));
            return ;
        }
        //装备使用
        int id = GetBean.getRoleService().equip(accountId,rId,pId);
        //装备错误
        if (id == -1){
            session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,"您的等级未达到要求"));
            //装备返回
            GetBean.getBackpackService().obtainProp(accountId, propsResource,1);
            return ;
        }
        if (id != 0){
            //获取旧的装备
            PropsResource oldEquipment  = GetBean.getPropsManager().getProps(id);
            //旧的装备存到背包
            GetBean.getBackpackService().obtainProp(accountId,oldEquipment,1);
        }
        session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE, propsResource.getName()+"使用成功！"));
        return ;
    }
}
