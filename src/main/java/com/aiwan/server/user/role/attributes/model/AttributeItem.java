package com.aiwan.server.user.role.attributes.model;

/**
 * @author dengzebiao
 * @since 2019.6.10
 * 属性项
 * */
public class AttributeItem {

    /** 属性名字 */
    private String name;

    /** 属性值 */
    private int value;

    public String getName() {
        return name;
    }

    public AttributeItem setName(String name) {
        this.name = name;
        return this;
    }

    public int getValue() {
        return value;
    }

    public AttributeItem setValue(int value) {
        this.value = value;
        return this;
    }
}
