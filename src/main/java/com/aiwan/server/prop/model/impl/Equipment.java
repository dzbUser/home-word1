package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Map;


/**
 * @author dengzebiao
 * @since 2019.6.19
 * 装备类型
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
public class Equipment extends AbstractProps {

    public final static int length = 3;

    @Override
    public int propUser(String accountId, Long rId) {
        //获取道具类
        PropsResource propsResource = getPropsResource();
        //扣除道具
        int status = GetBean.getBackpackService().deductionProp(accountId, this);
        if (status == 0) {
            return PromptCode.NOPROPINBACK;
        }
        //装备使用
        Equipment equipment = GetBean.getEquipmentService().equip(accountId, rId, this);
        //装备错误
        if (equipment == null) {
            //装备返回,未达到等级要求
            GetBean.getBackpackService().obtainNoOverlayProp(accountId, this);
            return PromptCode.NOREQUIREMENTINLEVEL;
        }
        if (equipment.getId() != 0) {
            //旧的装备存到背包
            GetBean.getBackpackService().obtainNoOverlayProp(accountId, equipment);
        }
        return PromptCode.USERSUCCESS;
    }

    /**
     * 获取装备属性
     */
    @JsonIgnore
    public Map<AttributeType, AttributeElement> getAttribute() {
        return getPropsResource().getAttributeMap();
    }

    /**
     * 获取装备位置
     */
    @JsonIgnore
    public int getPosition() {
        return getPropsResource().getPosition();
    }

    /**
     * 获取空装备
     */
    @JsonIgnore
    public static Equipment getEmpty() {
        Equipment equipment = new Equipment();
        equipment.init(0);
        return equipment;
    }

}
