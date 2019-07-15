package com.aiwan.server.user.role.attributes.model.impl;

import com.aiwan.server.user.role.attributes.model.AttributeId;

/**
 * 战斗单位属性模块
 *
 * @author dengzebiao
 * @since 2019.7.15
 */
public enum FightAttributeModule implements AttributeId {

    /**
     * 角色模块
     */
    ROLE_MODULE("role"),

    /**
     * buff模块
     */
    BUFF_MODULE("buff");


    /**
     * 模块名字
     */
    private String name;

    @Override
    public String getName() {
        return name;
    }

    FightAttributeModule(String name) {
        this.name = name;
    }
}
