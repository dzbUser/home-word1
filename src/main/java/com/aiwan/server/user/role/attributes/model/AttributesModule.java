package com.aiwan.server.user.role.attributes.model;

import com.aiwan.server.util.GetBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebia
 * @since 2019.6.12
 * 属性模块枚举类
 * */
public enum  AttributesModule implements Serializable {
    /** 获取人物属性模块 */
    ROLE("role"){
        @Override
        public Map<AttributeType,AttributeElement> getAttributes(Long rId){
            return GetBean.getRoleService().getAttributes(rId);
        }
    },

    /** 获取坐骑属性 */
    MOUNT("mount"){
        @Override
        public Map<AttributeType,AttributeElement> getAttributes(Long rId){
            return GetBean.getMountService().getAttributes(rId);
        }
    },

    /**
     * 装备模块
     */
    EQUIP("equip"){
        @Override
        public Map<AttributeType,AttributeElement> getAttributes(Long rId){
            return GetBean.getEquipmentService().getEquipAttributes(rId);
        }
    },
    /**
     * buff模块
     */
    BUFF("buff") {
        @Override
        public Map<AttributeType, AttributeElement> getAttributes(Long rId) {
            return new HashMap<>();
        }
    }
    ;
    /** 获取属性模块 */
    public Map<AttributeType,AttributeElement> getAttributes(Long rId){
        return null;
    }

    /** 获取模块类型 */
    public static AttributesModule getType(String name){
        for (AttributesModule attributesModule:values()){
            if (attributesModule.getName() == name){
                return attributesModule;
            }
        }
        throw new RuntimeException("匹配错误:" + name);
    }

    /** 模块名字 */
    private String name;

    AttributesModule(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public AttributesModule setName(String name) {
        this.name = name;
        return this;
    }
}
