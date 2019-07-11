package com.aiwan.server.user.role.skill.impact.impl;

import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.skill.impact.ImpactInterface;
import com.aiwan.server.util.FightUtil;

/**
 * 伤害效果
 *
 * @author dengzebiao
 * @since 2019.7.10
 */
public class HurtImpact implements ImpactInterface {

    @Override
    public void takeImpact(BaseUnit active, BaseUnit passive, int value) {
        long hurt = FightUtil.calculateFinalHurt(active.getFinalAttribute(), passive.getFinalAttribute(), value);
        passive.deduceHP(active.getId(), hurt);
    }
}
