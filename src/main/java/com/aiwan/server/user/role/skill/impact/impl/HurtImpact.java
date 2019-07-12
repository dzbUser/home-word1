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
 * 伤害效果
 *
 * @author dengzebiao
 * @since 2019.7.10
 */
public class HurtImpact implements ImpactInterface {

    @Override
    public void takeImpact(BaseUnit active, BaseUnit passive, ImpactAnalysis impactAnalysis, SkillUseContext skillUseContext) {
        int value = impactAnalysis.getValue();
        if (value != 0) {
            Long hurt = FightUtil.calculateFinalHurt(active.getFinalAttribute(), passive.getFinalAttribute(), value);
            passive.deduceHP(active.getId(), hurt);
            //存入最终伤害
            skillUseContext.putParameter(SkillUseContextEnum.SKILL_FINAL_DAMAGE, hurt);
        }
    }
}
