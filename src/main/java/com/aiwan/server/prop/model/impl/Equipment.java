package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.util.GetBean;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * @author dengzebiao
 * @since 2019.6.19
 * 装备类型
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
public class Equipment extends AbstractProps {

    @Override
    public boolean propUser(String accountId, Long rId) {
        //获取道具类
        PropsResource propsResource = getPropsResource();
        //扣除道具
        int status = GetBean.getBackpackService().deductionProp(accountId, propsResource.getId());
        if (status == 0) {
            return false;
        }
        //装备使用
        int id = GetBean.getEquipmentService().equip(accountId, rId, propsResource.getId());
        //装备错误
        if (id == -1) {
            //装备返回
            GetBean.getBackpackService().obtainProp(accountId, propsResource.getId());
            return false;
        }
        if (id != 0) {
            //获取旧的装备
            PropsResource oldEquipment = GetBean.getPropsManager().getPropsResource(id);
            //旧的装备存到背包
            GetBean.getBackpackService().obtainProp(accountId, propsResource.getId());
        }
        return true;
    }

}
