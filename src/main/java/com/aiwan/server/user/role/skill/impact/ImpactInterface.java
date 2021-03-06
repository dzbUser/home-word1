package com.aiwan.server.user.role.skill.impact;

import com.aiwan.server.user.role.fight.context.SkillUseContext;
import com.aiwan.server.user.role.fight.pvpunit.BaseUnit;
import com.aiwan.server.user.role.skill.resource.ImpactAnalysis;

/**
 * 效果接口
 *
 * @author dengzebiao
 * @since 2019.7.10
 */
public interface ImpactInterface {

    /**
     * 效果产生
     *
     * @param active 释放者
     * @param passive 目标
     * @param impactAnalysis 技能效果静态参数解析类
     * @param skillUseContext 技能使用上下文
     */
    void takeImpact(BaseUnit active, BaseUnit passive, ImpactAnalysis impactAnalysis, SkillUseContext skillUseContext);

}
