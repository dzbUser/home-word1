package com.aiwan.server.user.role.skill.impact;

import com.aiwan.server.user.role.fight.context.SkillUseContext;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.skill.resource.SkillLevelResource;

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
     * @param active
     * @param passive
     * @param skillLevelResource 节能资源
     */
    void takeImpact(BaseUnit active, BaseUnit passive, SkillLevelResource skillLevelResource, SkillUseContext skillUseContext);

}
