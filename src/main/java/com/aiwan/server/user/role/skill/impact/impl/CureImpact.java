package com.aiwan.server.user.role.skill.impact.impl;

import com.aiwan.server.user.role.fight.context.SkillUseContext;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.skill.impact.ImpactInterface;
import com.aiwan.server.user.role.skill.impact.ImpactType;
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
    public void takeImpact(BaseUnit active, BaseUnit passive, SkillLevelResource skillLevelResource, SkillUseContext skillUseContext) {
        Integer value = skillLevelResource.getImpactMap().get(ImpactType.CURE_IMPACT);
        if (value != null) {
            long cure = FightUtil.caculateCureBlood(active.getFinalAttribute(), skillLevelResource.getSkillAttack());
            passive.cureHp(cure);
        }
    }
}
