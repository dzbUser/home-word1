package com.aiwan.server.user.role.skill.impact.impl;

import com.aiwan.server.user.role.fight.context.SkillUseContext;
import com.aiwan.server.user.role.fight.context.SkillUseContextEnum;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.skill.impact.ImpactInterface;
import com.aiwan.server.user.role.skill.impact.ImpactType;
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
    public void takeImpact(BaseUnit active, BaseUnit passive, SkillLevelResource skillLevelResource, SkillUseContext skillUseContext) {
        Integer value = skillLevelResource.getImpactMap().get(ImpactType.HURT_IMPACT);
        if (value != null) {
            Long hurt = FightUtil.calculateFinalHurt(active.getFinalAttribute(), passive.getFinalAttribute(), value);
            passive.deduceHP(active.getId(), hurt);
            //存入最终伤害
            skillUseContext.putParameter(SkillUseContextEnum.SKILL_FINAL_DAMAGE, hurt);
        }
    }
}
