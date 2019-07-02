package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.user.backpack.model.Backpack;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.attributes.model.ImmutableAttributeElement;
import com.aiwan.server.user.role.player.model.Role;
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
    public int propUse(String accountId, Long rId, int num) {

        //获取背包
        Backpack backpack = GetBean.getBackPackManager().load(accountId);
        //获取道具类
        PropsResource propsResource = getPropsResource();
        //判断是否达到要求
        Role role = GetBean.getRoleManager().load(rId);
        if (role.getLevel() < propsResource.getLevel()) {
            //等级达不到要求等级
            return PromptCode.NOREQUIREMENTINLEVEL;
        }
        //扣除道具
        backpack.deductionPropByObjectId(getObjectId(), 1);
        //装备使用
        Equipment equipment = GetBean.getEquipmentService().equip(accountId, rId, this);
        if (equipment != null) {
            //旧的装备存到背包
            backpack.putNoOverlayProps(equipment);
        }
        //写回
        GetBean.getBackPackManager().writeBack(backpack);
        //装备属性修改

        return PromptCode.USERSUCCESS;
    }

    /**
     * 获取装备属性
     */
    @JsonIgnore
    public Map<AttributeType, AttributeElement> getAttribute() {
        return ImmutableAttributeElement.wrapper(getPropsResource().getAttributeMap());
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
