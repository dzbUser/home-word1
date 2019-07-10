package com.aiwan.server.user.role.skill.impact;

import com.aiwan.server.scenes.fight.model.pvpunit.BaseUnit;

/**
 * 效果接口
 *
 * @author dengzebiao
 * @since 2019.7.10
 */
public interface ImpactInterface {

    void takeImpact(BaseUnit active, BaseUnit passive, int value);
}
