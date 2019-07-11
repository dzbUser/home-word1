package com.aiwan.server.user.role.buff.effect;

import com.aiwan.server.user.role.buff.effect.impl.CureFightBuff;
import com.aiwan.server.user.role.buff.effect.impl.HurtFightBuff;

/**
 * 战斗buff枚举类
 *
 * @author dengzebiao
 * @since 2019.7.11
 */
public enum FightBuffType {

    /**
     * 伤害buff
     */
    HURT_EFFECT(1, HurtFightBuff.class),

    /**
     * 治疗Buff
     */
    CURE_EFFECT(2, CureFightBuff.class);

    private int type;

    private Class<? extends AbstractFightBuff> clzz;

    public static FightBuffType getEffectType(int type) {
        for (FightBuffType fightBuffType : values()) {
            if (type == fightBuffType.getType()) {
                return fightBuffType;
            }
        }

        throw new IllegalArgumentException("找不到Buff类型,type:" + type);
    }

    /**
     * 获取对应的实例
     */
    public AbstractFightBuff creator() {
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

    public Class<? extends AbstractFightBuff> getClzz() {
        return clzz;
    }

    public void setClzz(Class<? extends AbstractFightBuff> clzz) {
        this.clzz = clzz;
    }

    FightBuffType(int type, Class<? extends AbstractFightBuff> clzz) {
        this.type = type;
        this.clzz = clzz;
    }
}
