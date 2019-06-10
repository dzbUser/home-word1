package com.aiwan.server.user.role.attributes.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dengzebiao
 * @since 2019.6.10
 * 属性模块
 * */
public class AttributeModule {

    /** 属性项映射 */
    private Map<String,AttributeItem> attributeItemMap = new ConcurrentHashMap<>();

    /** 存入属性项 */
    public void putAttributeItem(AttributeItem attributeItem){
        attributeItemMap.put(attributeItem.getName() ,attributeItem);
    }

    /** 获取属性项 */
    public AttributeItem getAttributeItem(String name){
        return attributeItemMap.get(name);
    }

    public Map<String, AttributeItem> getAttributeItemMap() {
        return attributeItemMap;
    }

    public AttributeModule setAttributeItemMap(Map<String, AttributeItem> attributeItemMap) {
        this.attributeItemMap = attributeItemMap;
        return this;
    }
}
