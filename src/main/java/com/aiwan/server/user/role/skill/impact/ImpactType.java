package com.aiwan.server.user.role.skill.impact;

import com.aiwan.server.user.role.skill.impact.impl.CureImpact;
import com.aiwan.server.user.role.skill.impact.impl.HurtImpact;

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
    CURE_IMPACT(2, CureImpact.class);
    private int impactType;

    private Class<? extends ImpactInterface> clzz;

    public static ImpactType getImpactType(int type) {
        for (ImpactType impactType : values()) {
            if (type == impactType.getImpactType()) {
                return impactType;
            }
        }

        throw new IllegalArgumentException("找不到Buff类型,type:" + type);
    }

    /**
     * 获取对应的实例
     */
    public ImpactInterface creator() {
        try {
            return clzz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("生成Buff错误");
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
