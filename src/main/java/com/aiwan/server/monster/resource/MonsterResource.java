package com.aiwan.server.monster.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.publicsystem.annotation.Resource;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;

import java.util.HashMap;
import java.util.Map;

/**
 * 怪物资源
 *
 * @author dengzebiao
 * @since 2019.7.4
 */
@Resource("staticresource/monster.xls")
public class MonsterResource {

    /**
     * 怪物资源id
     */
    @CellMapping(name = "id")
    private int resourceId;

    /**
     * 怪物名字
     */
    @CellMapping(name = "name")
    private String name;

    /**
     * 怪物等级
     */
    @CellMapping(name = "level")
    private int level;

    /**
     * 怪物属性字符串
     */
    @CellMapping(name = "attribute")
    private String attributeString;

    /**
     * 怪物所在地图
     */
    @CellMapping(name = "map")
    private int map;

    /**
     * 怪物数量
     */
    @CellMapping(name = "num")
    private int num;

    /**
     * 道具掉落
     */
    @CellMapping(name = "drop")
    private String dropPropString;

    /**
     * 真实属性
     */
    private Map<AttributeType, AttributeElement> attributeMap = new HashMap<>();

    /**
     * 掉落道具,propId->num
     */
    private Map<Integer, Integer> dropMap = new HashMap<>();

    /**
     * 初始化
     */
    public void init() {
        String[] attributeStrings = attributeString.split(" ");
        //初始化属性
        for (String element : attributeStrings) {
            String[] item = element.split(":");
            int type = Integer.parseInt(item[0]);
            int value = Integer.parseInt(item[1]);
            AttributeType attributeType = AttributeType.getType(type);
            attributeMap.put(attributeType, AttributeElement.valueOf(attributeType, value));
        }

        //初始化属性掉落
        String[] dropStrings = dropPropString.split(" ");
        for (String element : dropStrings) {
            String[] item = element.split(":");
            int propId = Integer.parseInt(item[0]);
            int num = Integer.parseInt(item[1]);
            dropMap.put(propId, num);
        }

    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAttributeString() {
        return attributeString;
    }

    public void setAttributeString(String attributeString) {
        this.attributeString = attributeString;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDropPropString() {
        return dropPropString;
    }

    public void setDropPropString(String dropPropString) {
        this.dropPropString = dropPropString;
    }

    public Map<AttributeType, AttributeElement> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<AttributeType, AttributeElement> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public Map<Integer, Integer> getDropMap() {
        return dropMap;
    }

    public void setDropMap(Map<Integer, Integer> dropMap) {
        this.dropMap = dropMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
