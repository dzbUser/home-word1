package com.aiwan.server.user.role.skill.impact.impl;

import com.aiwan.server.scenes.fight.model.pvpunit.BaseUnit;
import com.aiwan.server.user.role.skill.impact.ImpactInterface;
import com.aiwan.server.util.FightUtil;

/**
 * 治疗效果
 *
 * @author dengzebiao
 * @since 2019.7.10
 */
public class CureImpact implements ImpactInterface {
    @Override
    public void takeImpact(BaseUnit active, BaseUnit passive, int value) {
        long cure = FightUtil.caculateCureBlood(active.getFinalAttribute(), value);
        passive.cureHp(cure);
    }
}
