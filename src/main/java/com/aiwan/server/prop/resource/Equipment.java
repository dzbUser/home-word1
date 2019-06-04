package com.aiwan.server.prop.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;

/**
 * @author dengzebiao
 * @since 2019.6.3
 * 装备静态初始化类
 * */
public class Equipment {
    /** 唯一标识*/
    @CellMapping(name = "id")
    private int id;

    /** 攻击力 */
    @CellMapping(name = "power")
    private int power;

    /** 血量 */
    @CellMapping(name = "blood")
    private int blood;

    /** 攻击加成 */
    @CellMapping(name = "powerBonus")
    private int powerBonus;


    /** 装备位置 */
    @CellMapping(name = "position")
    private int position;

    public int getId() {
        return id;
    }

    public Equipment setId(int id) {
        this.id = id;
        return this;
    }

    public int getPower() {
        return power;
    }

    public Equipment setPower(int power) {
        this.power = power;
        return this;
    }

    public int getBlood() {
        return blood;
    }

    public Equipment setBlood(int blood) {
        this.blood = blood;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public Equipment setPosition(int position) {
        this.position = position;
        return this;
    }

    public int getPowerBonus() {
        return powerBonus;
    }

    public Equipment setPowerBonus(int powerBonus) {
        this.powerBonus = powerBonus;
        return this;
    }
}
