package com.aiwan.server.user.role.skill.impact.impl;

import com.aiwan.server.user.role.fight.context.SkillUseContext;
import com.aiwan.server.user.role.fight.context.SkillUseContextEnum;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.skill.impact.ImpactInterface;
import com.aiwan.server.user.role.skill.impact.ImpactType;
import com.aiwan.server.user.role.skill.resource.ImpactAnalysis;
import com.aiwan.server.user.role.skill.resource.SkillLevelResource;
import com.aiwan.server.util.FightUtil;

/**
 * 吸血效果
 *
 * @author dengzebiao
 * @since 2019.7.11
 */
public class SuckImpact implements ImpactInterface {
    @Override
    public void takeImpact(BaseUnit active, BaseUnit passive, ImpactAnalysis impactAnalysis, SkillUseContext skillUseContext) {
        int value = impactAnalysis.getValue();
        Long hurt = (Long) skillUseContext.getParameter(SkillUseContextEnum.SKILL_FINAL_DAMAGE);
        if (value != 0 && hurt != null) {
            long cure = hurt * value / 10000;
            active.cureHp(cure);
        }
    }
}
