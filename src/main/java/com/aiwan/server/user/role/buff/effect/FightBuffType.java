package com.aiwan.server.user.role.buff.effect;

import com.aiwan.server.user.role.buff.effect.impl.AttributeFightBuff;
import com.aiwan.server.user.role.buff.effect.impl.CureFightBuff;
import com.aiwan.server.user.role.buff.effect.impl.HurtFightBuff;
import com.aiwan.server.user.role.buff.resource.IFightBuffAnalysis;
import com.aiwan.server.user.role.buff.resource.bean.AttributeFightBuffBean;
import com.aiwan.server.user.role.buff.resource.bean.CureFightBuffBean;
import com.aiwan.server.user.role.buff.resource.bean.HurtFightBuffBean;

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
    HURT_EFFECT(1, HurtFightBuff.class, HurtFightBuffBean.class),

    /**
     * 治疗Buff
     */
    CURE_EFFECT(2, CureFightBuff.class, CureFightBuffBean.class),

    /**
     * 属性buff
     */
    ATTRIBUTE_BUFF(3, AttributeFightBuff.class, AttributeFightBuffBean.class);

    private int type;

    /** buff效果类 */
    private Class<? extends AbstractFightBuff> clzz;

    /**
     * 效果参数类
     */
    private Class<? extends IFightBuffAnalysis> anaClazz;

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

    public Class<? extends IFightBuffAnalysis> getAnaClazz() {
        return anaClazz;
    }

    public void setAnaClazz(Class<? extends IFightBuffAnalysis> anaClazz) {
        this.anaClazz = anaClazz;
    }

    FightBuffType(int type, Class<? extends AbstractFightBuff> clzz, Class<? extends IFightBuffAnalysis> anaClazz) {
        this.type = type;
        this.clzz = clzz;
        this.anaClazz = anaClazz;
    }
}
