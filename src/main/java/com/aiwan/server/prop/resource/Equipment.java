package com.aiwan.server.prop.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.3
 * 装备静态初始化类
 * */
public class Equipment {
    /** 唯一标识*/
    @CellMapping(name = "id")
    private int id;

    /** 攻击力 */
    @CellMapping(name = "power")
    private int power;

    /** 血量 */
    @CellMapping(name = "blood")
    private int blood;

    /** 攻击加成 */
    @CellMapping(name = "powerBonus")
    private int powerBonus;


    /** 装备位置 */
    @CellMapping(name = "position")
    private int position;

    /** 等级限制 */
    @CellMapping(name = "level")
    private int level;

    @CellMapping(name = "attribute")
    private String attribute;

    /** 属性序号-》属性的映射 */
    private Map<Integer,Integer> map = new HashMap<Integer,Integer>();
    public void init(){
        String[] attributeString = attribute.split(" ");
        for (String element:attributeString){
            String[] item = element.split(":");
            map.put(Integer.parseInt(item[0]),Integer.parseInt(item[1]));
        }
    }

    public int getId() {
        return id;
    }

    public Equipment setId(int id) {
        this.id = id;
        return this;
    }

    public int getPower() {
        return power;
    }

    public Equipment setPower(int power) {
        this.power = power;
        return this;
    }

    public int getBlood() {
        return blood;
    }

    public Equipment setBlood(int blood) {
        this.blood = blood;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public Equipment setPosition(int position) {
        this.position = position;
        return this;
    }

    public int getPowerBonus() {
        return powerBonus;
    }

    public Equipment setPowerBonus(int powerBonus) {
        this.powerBonus = powerBonus;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public Equipment setLevel(int level) {
        this.level = level;
        return this;
    }

    public String getAttribute() {
        return attribute;
    }

    public Equipment setAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public Map<Integer, Integer> getMap() {
        return map;
    }

    public Equipment setMap(Map<Integer, Integer> map) {
        this.map = map;
        return this;
    }

    @Override
    public String toString(){
        return  " 攻击力:"+power+
                " 血量:"+blood+
                " 攻击加成:"+powerBonus+
                " 等级要求:"+level;
    }
}
