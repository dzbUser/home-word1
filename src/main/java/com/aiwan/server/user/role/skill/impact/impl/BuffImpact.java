package com.aiwan.server.user.role.skill.impact.impl;

import com.aiwan.server.user.role.buff.effect.AbstractFightBuff;
import com.aiwan.server.user.role.buff.effect.FightBuffType;
import com.aiwan.server.user.role.buff.resource.EffectResource;
import com.aiwan.server.user.role.fight.context.SkillUseContext;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.skill.impact.ImpactInterface;
import com.aiwan.server.user.role.skill.impact.ImpactType;
import com.aiwan.server.user.role.skill.resource.ImpactAnalysis;
import com.aiwan.server.user.role.skill.resource.SkillLevelResource;
import com.aiwan.server.util.GetBean;

/**
 * buff效果
 *
 * @author dengzebiao
 * @since 2019.7.11
 */
public class BuffImpact implements ImpactInterface {

    @Override
    public void takeImpact(BaseUnit active, BaseUnit passive, ImpactAnalysis impactAnalysis, SkillUseContext skillUseContext) {
        //获取buff静态资源
        EffectResource effectResource = GetBean.getBuffManager().getEffectResource(impactAnalysis.getEffectId());
        AbstractFightBuff abstractFightBuff = FightBuffType.getEffectType(effectResource.getType()).creator();
        if (abstractFightBuff != null) {
            long now = System.currentTimeMillis();
            if (effectResource.getIsPeriod() == 1) {
                abstractFightBuff.init(effectResource.getId(), now, effectResource.getPeriod(), effectResource.getDuration(), now + effectResource.getPeriod(), active);
            } else {
                abstractFightBuff.init(effectResource.getId(), now, effectResource.getPeriod(), effectResource.getDuration(), now + effectResource.getDuration(), active);
            }
            passive.putBuff(abstractFightBuff.getEffectResource().getUniqueId(), abstractFightBuff);
        }
    }
}
