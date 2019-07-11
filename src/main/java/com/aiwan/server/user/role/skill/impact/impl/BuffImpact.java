package com.aiwan.server.user.role.skill.impact.impl;

import com.aiwan.server.user.role.buff.effect.AbstractEffect;
import com.aiwan.server.user.role.fight.context.SkillUseContext;
import com.aiwan.server.user.role.fight.context.SkillUseContextEnum;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.skill.impact.ImpactInterface;
import com.aiwan.server.user.role.skill.resource.SkillLevelResource;

/**
 * buff效果
 */
public class BuffImpact implements ImpactInterface {

    @Override
    public void takeImpact(BaseUnit active, BaseUnit passive, SkillLevelResource skillLevelResource, SkillUseContext skillUseContext) {
        //添加buff
        AbstractEffect abstractEffect = (AbstractEffect) skillUseContext.getParameter(SkillUseContextEnum.BUFF_EFFECT);
        if (abstractEffect != null) {
            passive.putBuff(abstractEffect.getEffectResource().getUniqueId(), abstractEffect);
        }
    }
}
