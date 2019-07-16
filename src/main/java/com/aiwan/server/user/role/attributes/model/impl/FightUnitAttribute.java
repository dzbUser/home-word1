package com.aiwan.server.user.role.attributes.model.impl;

import com.aiwan.server.user.role.attributes.model.AttributeContainer;

import java.util.HashMap;

/**
 * 战斗单位属性
 *
 * @author dengzebiao
 * @since 2019.7.15
 */
public class FightUnitAttribute extends AttributeContainer {

    public FightUnitAttribute() {
        for (FightAttributeModule fightAttributeModule : FightAttributeModule.values()) {
            getModuleMap().put(fightAttributeModule, new HashMap<>());
        }
    }

}
