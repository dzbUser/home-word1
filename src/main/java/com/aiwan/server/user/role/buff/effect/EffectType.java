package com.aiwan.server.user.role.buff.effect;

import com.aiwan.server.user.role.buff.effect.impl.CureEffect;
import com.aiwan.server.user.role.buff.effect.impl.HurtEffect;

public enum EffectType {

    /**
     * 伤害buff
     */
    HURT_EFFECT(1, HurtEffect.class),

    /**
     * 治疗Buff
     */
    CURE_EFFECT(2, CureEffect.class);

    private int type;

    private Class<? extends AbstractEffect> clzz;

    public static EffectType getEffectType(int type) {
        for (EffectType effectType : values()) {
            if (type == effectType.getType()) {
                return effectType;
            }
        }

        throw new IllegalArgumentException("找不到Buff类型,type:" + type);
    }

    /**
     * 获取对应的实例
     */
    public AbstractEffect creator() {
        try {
            return clzz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("生成Buff错误");
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Class<? extends AbstractEffect> getClzz() {
        return clzz;
    }

    public void setClzz(Class<? extends AbstractEffect> clzz) {
        this.clzz = clzz;
    }

    EffectType(int type, Class<? extends AbstractEffect> clzz) {
        this.type = type;
        this.clzz = clzz;
    }
}
