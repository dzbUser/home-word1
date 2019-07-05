package com.aiwan.server.user.role.buff.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.publicsystem.annotation.Resource;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;

import java.util.HashMap;
import java.util.Map;

@Resource("staticresource/buff.xls")
public class BuffResource {

    /**
     * buff唯一id
     */
    @CellMapping(name = "id")
    private int id;

    @CellMapping(name = "attribute")
    private String attribute;

    @CellMapping(name = "buffType")
    private int buffType;

    /**
     * 存放buff属性
     */
    Map<AttributeType, AttributeElement> attributeMap = new HashMap<AttributeType, AttributeElement>();

    /**
     * 初始化
     */
    public void init() {
        //初始化
        String empty = "empty";
        if (attribute.equals(empty)) {
            //没有属性值
            attributeMap = null;
            return;
        }
        String[] attributeString = attribute.split(" ");
        for (String element : attributeString) {
            String[] item = element.split(":");
            AttributeType attributeType = AttributeType.getType(Integer.parseInt(item[0]));
            //属性转化
            attributeMap.put(attributeType, AttributeElement.valueOf(attributeType, Integer.parseInt(item[1])));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<AttributeType, AttributeElement> getAttributeMap() {
        return attributeMap;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public int getBuffType() {
        return buffType;
    }

    public void setBuffType(int buffType) {
        this.buffType = buffType;
    }
}
