package com.aiwan.server.user.role.buff.effect.impl;

import com.aiwan.server.user.role.buff.effect.AbstractEffect;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.util.FightUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 伤害buff
 */
public class HurtEffect extends AbstractEffect {

    Logger logger = LoggerFactory.getLogger(HurtEffect.class);

    @Override
    public void doActive(BaseUnit passive) {
        long hurt = FightUtil.calculateFinalHurt(getActiveUnit().getFinalAttribute(), passive.getFinalAttribute(), getEffectResource().getValue());
        passive.deduceHP(getActiveUnit().getId(), hurt);
        logger.debug("伤害buff造成效果");
    }
}
