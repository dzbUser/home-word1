package com.aiwan.server.user.role.skill.model.impl;

import com.aiwan.server.scenes.fight.model.pvpunit.BaseUnit;
import com.aiwan.server.scenes.fight.model.pvpunit.FighterRole;
import com.aiwan.server.user.role.skill.model.AbstractSkill;
import com.aiwan.server.util.FightUtil;

/**
 * @author dengzebiao
 * @since 2019.7.1
 * 普通造成单体伤害技能
 */
public class CommonSkill extends AbstractSkill {

    @Override
    public void doUserSkill(BaseUnit active, BaseUnit passive) {
        //计算最终伤害
        long hurt = FightUtil.calculateFinalHurt(active.getFinalAttribute(), passive.getFinalAttribute(), getSkillLevelResouece().getSkillAttack());
        //扣除目标血量
        passive.deduceHP(active.getId(), hurt);
    }
}
