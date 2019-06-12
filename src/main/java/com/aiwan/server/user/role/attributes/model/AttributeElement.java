package com.aiwan.server.user.role.attributes.model;

/**
 * @author dengzebiao
 * @since 2019.6.12
 * 属性单元
 */
public class AttributeElement {

    /** 属性类型 */
    private AttributeType attributeType;

    /** 属性值 */
    private long value;

    /** 获取属性单元 */
    public static AttributeElement valueOf(AttributeType attributeType,long value){
        AttributeElement attributeElement = new AttributeElement();
        attributeElement.setAttributeType(attributeType);
        attributeElement.setValue(value);
        return attributeElement;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public AttributeElement setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
        return this;
    }

    public long getValue() {
        return value;
    }

    public AttributeElement setValue(long value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString(){
        return attributeType.getDesc()+":"+value;
    }
}
