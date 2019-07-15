package com.aiwan.server.user.role.buff.resource.bean;

import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.buff.resource.EffectResource;
import com.aiwan.server.user.role.buff.resource.IFightBuffAnalysis;

import java.util.HashMap;
import java.util.Map;

/**
 * 属性战斗buff参数
 */
public class AttributeFightBuffBean implements IFightBuffAnalysis {

    /**
     * 属性增加参数
     */
    private Map<AttributeType, AttributeElement> attributes = new HashMap<>();

    @Override
    public void doParse(EffectResource effectResource) {
        String[] attributeParameter = ((String) effectResource.getValueMap().get("attribute")).split("/");
        for (String attribute : attributeParameter) {
            String[] attributeValue = attribute.split("=");
            AttributeType attributeType = AttributeType.getType(Integer.parseInt(attributeValue[0]));
            this.attributes.put(attributeType, AttributeElement.valueOf(attributeType, Long.parseLong(attributeValue[1])));
        }
    }

    public Map<AttributeType, AttributeElement> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<AttributeType, AttributeElement> attributes) {
        this.attributes = attributes;
    }
}
