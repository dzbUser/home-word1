package com.aiwan.server.role.attributes.model;

/**
 * @author dengzebiao
 * @since 2019.6.6
 *
 * */
public class Attributes {
    /** 攻击力 */
    private int power;

    /** 血量 */
    private int blood;

    /** 攻击力加成 */
    private int powerBonus;

    public Attributes() {
        this.power = 0;
        this.blood = 0;
        this.powerBonus = 0;
    }

    public int getPower() {
        return power;
    }

    public Attributes setPower(int power) {
        this.power = power;
        return this;
    }

    public int getBlood() {
        return blood;
    }

    public Attributes setBlood(int blood) {
        this.blood = blood;
        return this;
    }

    public int getPowerBonus() {
        return powerBonus;
    }

    public Attributes setPowerBonus(int powerBonus) {
        this.powerBonus = powerBonus;
        return this;
    }
}
