package com.aiwan.server.prop.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.publicsystem.annotation.Resource;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiap
 * @since 2019.6.3
 * 道具静态初始化类
 * */
@Resource("staticresource/prop.xls")
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
     * 装备位置
     */
    @CellMapping(name = "position")
    private int position;

    /**
     * 装备等级
     */
    @CellMapping(name = "level")
    private int level;

    /**
     * 背包内可存上限
     */
    @CellMapping(name = "limit")
    private int limit;

    /**
     * 道具效果
     */
    @CellMapping(name = "effect")
    private int effect;

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
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("名字:" + name);
        stringBuffer.append(" 描述:" + introduce + "\n");
        if (attributeMap != null) {
            //属性不为空
            stringBuffer.append("属性:");
            for (Map.Entry<AttributeType, AttributeElement> entry : attributeMap.entrySet()) {
                if (entry.getKey().isRateAttribute()) {
                    stringBuffer.append(" " + entry.getKey().getDesc() + ":" + (entry.getValue().getValue() / 100) + "%");
                } else {
                    stringBuffer.append(" " + entry.getKey().getDesc() + ":" + entry.getValue().getValue());
                }
            }
            stringBuffer.append(" 等级要求:" + level);
        }
        return stringBuffer.toString();
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Map<AttributeType, AttributeElement> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<AttributeType, AttributeElement> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }
}
