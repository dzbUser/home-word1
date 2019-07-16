package com.aiwan.server.user.role.buff.effect.impl;

import com.aiwan.server.user.role.buff.effect.AbstractFightBuff;
import com.aiwan.server.user.role.buff.resource.bean.CureFightBuffBean;
import com.aiwan.server.user.role.fight.pvpunit.BaseUnit;
import com.aiwan.server.util.FightUtil;

/**
 * 治疗buff
 *
 * @author dengzebiao
 * @since 2019.7.11
 */
public class CureFightBuff extends AbstractFightBuff {

    /**
     * 最终治疗量
     */
    private long cure;

    @Override
    public void doActive(BaseUnit passive) {
        CureFightBuffBean cureFightBuffBean = (CureFightBuffBean) getEffectResource().getValueParameter();
        long cure = FightUtil.calculateCureBlood(getActiveUnit().getFinalAttribute(), cureFightBuffBean.getCure());
        passive.cureHp(cure);
    }
}
