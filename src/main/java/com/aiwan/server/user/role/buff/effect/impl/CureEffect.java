package com.aiwan.server.user.role.buff.effect.impl;

import com.aiwan.server.user.role.buff.effect.AbstractEffect;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.util.FightUtil;

public class CureEffect extends AbstractEffect {
    @Override
    public void doActive(BaseUnit passive) {
        long cure = FightUtil.caculateCureBlood(getActiveUnit().getFinalAttribute(), getEffectResource().getValue());
        passive.cureHp(cure);
    }
}
