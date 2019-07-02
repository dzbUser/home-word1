package com.aiwan.server.user.role.attributes.model;

import com.aiwan.server.util.AttributeUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.12
 * 属性类型
 * */
public enum  AttributeType implements Serializable {
    /** 攻击力 */
    ATTACK(1,"攻击力"){
        @Override
        public AttributeType[] getBeEffectAttribute(){
            return new AttributeType[]{ATTACK_RATE};
        }

        @Override
        public Long calculate(AttributeElement attributeElement, Map<AttributeType,AttributeElement> map){
            return AttributeUtil.calculateItem(attributeElement,map);
        }
    },

    /** 血量 */
    BLOOD(2,"血量"){

    },

    /** 攻击加成 */
    ATTACK_RATE(3,"攻击加成"){
        @Override
        public boolean isRateAttribute(){
            return true;
        }
        @Override
        public Long calculate(AttributeElement attributeElement, Map<AttributeType,AttributeElement> map){
            return attributeElement.getValue()/100;
        }
        @Override
        public AttributeType getEffectAttribute(){
            return ATTACK;
        }
    },
    /** 魔法基本属性 */
    MAGIC(4,"魔法"){

    },
    /**
     * 防御基本属性
     */
    DEFENSE(5, "防御") {

    },
    ;
    /** 属性id */
    private int id;

    /** 属性描述 */
    private String desc;

    /** 是否为万分比属性 */
    public boolean isRateAttribute(){
        return false;
    }

    /** 影响属性 */
    public AttributeType getEffectAttribute(){
        return null;
    }

    /** 获取影响该属性的属性 */
    public AttributeType[] getBeEffectAttribute(){
        return null;
    }

    public static AttributeType getType(int id){
        for (AttributeType attributeType:values()){
            if (attributeType.getId() == id){
                return attributeType;
            }
        }
        throw new RuntimeException("匹配错误:" + id);
    }

    AttributeType(int id,String desc){
        this.id = id;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public AttributeType setId(int id) {
        this.id = id;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public AttributeType setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    /** 计算最终属性 */
    public Long calculate(AttributeElement attributeElement, Map<AttributeType,AttributeElement> map){
        return attributeElement.getValue();
    }

}
