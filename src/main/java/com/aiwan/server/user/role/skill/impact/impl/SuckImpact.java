package com.aiwan.server.user.role.skill.impact.impl;

import com.aiwan.server.user.role.fight.context.SkillUseContext;
import com.aiwan.server.user.role.fight.context.SkillUseContextEnum;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.skill.impact.ImpactInterface;
import com.aiwan.server.user.role.skill.impact.ImpactType;
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
    public void takeImpact(BaseUnit active, BaseUnit passive, SkillLevelResource skillLevelResource, SkillUseContext skillUseContext) {
        Integer value = skillLevelResource.getImpactMap().get(ImpactType.SUCK_IMPACT);
        Long hurt = (Long) skillUseContext.getParameter(SkillUseContextEnum.SKILL_FINAL_DAMAGE);
        if (value != null && hurt != null) {
            long cure = hurt * value / 10000;
            active.cureHp(cure);
        }
    }
}