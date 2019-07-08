package com.aiwan.server.user.role.buff.effect;

public enum BuffType {

    /**
     * 伤害buff
     */
    HURT_BUFF(2, HurtBuffEffect.class),
    ;
    private int buffType;

    private Class<? extends AbstractBuffEffect> clzz;

    public static BuffType getBuffType(int type) {
        for (BuffType buffType : values()) {
            if (type == buffType.getBuffType()) {
                return buffType;
            }
        }

        throw new IllegalArgumentException("找不到Buff类型,type:" + type);
    }

    /**
     * 获取对应的实例
     */
    public AbstractBuffEffect createBuff() {
        try {
            return clzz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("生成Buff错误");
        }
    }

    public int getBuffType() {
        return buffType;
    }

    public void setBuffType(int buffType) {
        this.buffType = buffType;
    }

    public Class<? extends AbstractBuffEffect> getClzz() {
        return clzz;
    }

    public void setClzz(Class<? extends AbstractBuffEffect> clzz) {
        this.clzz = clzz;
    }

    BuffType(int buffType, Class<? extends AbstractBuffEffect> clzz) {
        this.buffType = buffType;
        this.clzz = clzz;
    }
}
