package com.aiwan.server.user.role.attributes.model.impl;

import com.aiwan.server.user.role.attributes.model.AttributeContainer;

/**
 * @author dengzebiao
 * @since 2019.6.12
 * 人物属性汇总
 */
public class RoleAttribute extends AttributeContainer {
    /**
     * 构造函数
     */
    public RoleAttribute(Long rId) {
        //初始化
        for (RoleAttributesModule module : RoleAttributesModule.values()) {
            moduleMap.put(module, module.getAttributes(rId));
        }
        //计算属性
        calculate();
    }
}
