package com.aiwan.server.user.role.buff.effect.impl;

import com.aiwan.server.user.role.buff.effect.AbstractFightBuff;
import com.aiwan.server.user.role.buff.resource.bean.AttributeFightBuffBean;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;

/**
 * 战斗属性buff
 *
 * @author dengzebiao
 * @since 2019.7.15
 */
public class AttributeFightBuff extends AbstractFightBuff {

    @Override
    public void doActive(BaseUnit passive) {
        AttributeFightBuffBean attributeFightBuffBean = (AttributeFightBuffBean) getEffectResource().getValueParameter();
        passive.removeBuffAttribute(attributeFightBuffBean.getAttributes());
    }

    @Override
    public boolean isAttributeBuff() {
        return true;
    }

}
