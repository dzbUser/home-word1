package com.aiwan.server.user.role.skill.impact;

import com.aiwan.server.user.role.skill.impact.impl.BuffImpact;
import com.aiwan.server.user.role.skill.impact.impl.CureImpact;
import com.aiwan.server.user.role.skill.impact.impl.HurtImpact;
import com.aiwan.server.user.role.skill.impact.impl.SuckImpact;

/**
 * 效果类型
 */
public enum ImpactType {

    /**
     * 伤害类型效果
     */
    HURT_IMPACT(1, HurtImpact.class),

    /**
     * 治疗类型效果
     */
    CURE_IMPACT(2, CureImpact.class),

    /**
     * 吸血,在成伤害的百分比
     */
    SUCK_IMPACT(3, SuckImpact.class),

    /**
     * buff技能效果
     */
    BUFF_IMPACT(4, BuffImpact.class);

    private int impactType;

    private Class<? extends ImpactInterface> clzz;

    public static ImpactType getImpactType(int type) {
        for (ImpactType impactType : values()) {
            if (type == impactType.getImpactType()) {
                return impactType;
            }
        }

        throw new IllegalArgumentException("找不到效果类型,type:" + type);
    }

    /**
     * 获取对应的实例
     */
    public ImpactInterface creator() {
        try {
            return clzz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("生成效果错误");
        }
    }

    public int getImpactType() {
        return impactType;
    }

    public void setImpactType(int impactType) {
        this.impactType = impactType;
    }

    public Class<? extends ImpactInterface> getClzz() {
        return clzz;
    }

    public void setClzz(Class<? extends ImpactInterface> clzz) {
        this.clzz = clzz;
    }

    ImpactType(int impactType, Class<? extends ImpactInterface> clzz) {
        this.impactType = impactType;
        this.clzz = clzz;
    }
}
