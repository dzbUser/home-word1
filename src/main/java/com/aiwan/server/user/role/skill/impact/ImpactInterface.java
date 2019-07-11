package com.aiwan.server.user.role.skill.impact;

import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;

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
     * @param value
     */
    void takeImpact(BaseUnit active, BaseUnit passive, int value);

}
