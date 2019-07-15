package com.aiwan.server.user.role.buff.effect.impl;

import com.aiwan.server.user.role.buff.effect.AbstractFightBuff;
import com.aiwan.server.user.role.buff.resource.bean.HurtFightBuffBean;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.util.FightUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 伤害buff
 *
 * @author dengzebiao
 * @since 2019.7.11
 */
public class HurtFightBuff extends AbstractFightBuff {

    Logger logger = LoggerFactory.getLogger(HurtFightBuff.class);

    @Override
    public void doActive(BaseUnit passive) {
        HurtFightBuffBean hurtFightBuffBean = (HurtFightBuffBean) getEffectResource().getValueParameter();
        long hurt = FightUtil.calculateFinalHurt(getActiveUnit().getFinalAttribute(), passive.getFinalAttribute(), hurtFightBuffBean.getAttack());
        passive.deduceHP(getActiveUnit().getId(), hurt);
        logger.debug("伤害buff造成效果");
    }
}
