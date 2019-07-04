package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.attributes.model.ImmutableAttributeElement;
import com.aiwan.server.util.PromptCode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

/**
 * buff道具
 *
 * @author dengzebiao
 */
public class BuffProps extends AbstractProps {

    @Override
    public int propUse(String accountId, Long rId, int num) {
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
     * 延时时间
     * 单位秒
     */
    @JsonIgnore
    public int getDelay() {
        return getPropsResource().getEffect();
    }

}
