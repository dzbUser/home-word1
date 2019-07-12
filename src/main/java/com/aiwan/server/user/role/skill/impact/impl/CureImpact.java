package com.aiwan.server.user.role.skill.impact.impl;

import com.aiwan.server.user.role.fight.context.SkillUseContext;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.skill.impact.ImpactInterface;
import com.aiwan.server.user.role.skill.impact.ImpactType;
import com.aiwan.server.user.role.skill.resource.ImpactAnalysis;
import com.aiwan.server.user.role.skill.resource.SkillLevelResource;
import com.aiwan.server.util.FightUtil;

/**
 * 治疗效果
 *
 * @author dengzebiao
 * @since 2019.7.10
 */
public class CureImpact implements ImpactInterface {
    @Override
    public void takeImpact(BaseUnit active, BaseUnit passive, ImpactAnalysis impactAnalysis, SkillUseContext skillUseContext) {
        int value = impactAnalysis.getValue();
        if (value != 0) {
            long cure = FightUtil.calculateCureBlood(active.getFinalAttribute(), value);
            passive.cureHp(cure);
        }
    }
}
