package com.aiwan.server.user.role.attributes.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 不可修改属性单元
 *
 * @author dengzebiao
 * @since 2019.7.2
 */
public class ImmutableAttributeElement extends AttributeElement {

    public static ImmutableAttributeElement wrapper(AttributeElement attributeElement) {
        ImmutableAttributeElement immutableAttributeElement = new ImmutableAttributeElement();
        immutableAttributeElement.attributeType = attributeElement.getAttributeType();
        immutableAttributeElement.value = attributeElement.getValue();
        return immutableAttributeElement;
    }

    /**
     * @return 实际类型Map<AttributeType, ImmutableAttributeElement>
     */
    public static Map<AttributeType, AttributeElement> wrapper(Map<AttributeType, AttributeElement> map) {
        if (map == null) {
            return Collections.emptyMap();
        }

        Map<AttributeType, AttributeElement> immMap = new HashMap<AttributeType, AttributeElement>(16);
        for (Map.Entry<AttributeType, AttributeElement> elementEntry : map.entrySet()) {
            immMap.put(elementEntry.getKey(), wrapper(elementEntry.getValue()));
        }
        return immMap;
    }

    @Override
    public AttributeType getAttributeType() {
        return attributeType;
    }

    @Override
    public AttributeElement setAttributeType(AttributeType attributeType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public AttributeElement setValue(long value) {
        throw new UnsupportedOperationException();
    }
}
