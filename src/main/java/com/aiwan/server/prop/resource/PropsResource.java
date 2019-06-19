package com.aiwan.server.prop.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiap
 * @since 2019.6.3
 * 道具静态初始化类
 * */
public class PropsResource{

    /** 道具id */
    @CellMapping(name = "id")
    private int id;

    /** 道具类型 */
    @CellMapping(name = "type")
    private int type;

    /** 道具名字 */
    @CellMapping(name = "name")
    private String name;

    /** 道具介绍 */
    @CellMapping(name = "introduce")
    private String introduce;

    /** 道具是否可叠加 */
    @CellMapping(name = "overlay")
    private int overlay;

    /** 是否可以使用 */
    @CellMapping(name = "use")
    private int use;

    /**
     * 装备属性，装备独有
     */
    @CellMapping(name = "attribute")
    private String attribute;

    /**
     * 存放装备属性
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

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PropsResource setType(int type) {
        this.type = type;
        return this;

    }

    public String getName() {
        return name;
    }

    public PropsResource setName(String name) {
        this.name = name;
        return this;
    }

    public String getIntroduce() {
        return introduce;
    }

    public PropsResource setIntroduce(String introduce) {
        this.introduce = introduce;
        return this;
    }

    public int getOverlay() {
        return overlay;
    }

    public PropsResource setOverlay(int overlay) {
        this.overlay = overlay;
        return this;
    }

    public int getUse() {
        return use;
    }

    public PropsResource setUse(int use) {
        this.use = use;
        return this;
    }

    @Override
    public String toString(){
        return "名字:"+name+
                " 描述:" + introduce;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
