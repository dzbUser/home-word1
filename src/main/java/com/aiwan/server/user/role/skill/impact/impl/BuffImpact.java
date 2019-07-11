package com.aiwan.server.user.role.skill.impact.impl;

import com.aiwan.server.user.role.buff.effect.AbstractFightBuff;
import com.aiwan.server.user.role.buff.effect.FightBuffType;
import com.aiwan.server.user.role.buff.resource.EffectResource;
import com.aiwan.server.user.role.fight.context.SkillUseContext;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.skill.impact.ImpactInterface;
import com.aiwan.server.user.role.skill.impact.ImpactType;
import com.aiwan.server.user.role.skill.resource.SkillLevelResource;
import com.aiwan.server.util.GetBean;

/**
 * buff效果
 */
public class BuffImpact implements ImpactInterface {

    @Override
    public void takeImpact(BaseUnit active, BaseUnit passive, SkillLevelResource skillLevelResource, SkillUseContext skillUseContext) {
        //获取buff静态资源
        EffectResource effectResource = GetBean.getBuffManager().getEffectResource(skillLevelResource.getImpactMap().get(ImpactType.BUFF_IMPACT));
        AbstractFightBuff abstractFightBuff = FightBuffType.getEffectType(effectResource.getType()).creator();
        long now = System.currentTimeMillis();
        abstractFightBuff.init(effectResource.getId(), now, effectResource.getPeriod(), effectResource.getDuration(), active);
        if (abstractFightBuff != null) {
            passive.putBuff(abstractFightBuff.getEffectResource().getUniqueId(), abstractFightBuff);
        }
    }
}
