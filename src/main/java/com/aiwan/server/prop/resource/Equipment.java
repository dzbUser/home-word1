package com.aiwan.server.prop.resource;

/**
 * @author dengzebiao
 * @since 2019.6.3
 * 装备静态初始化类
 * */
public class Equipment {
    private int id;
    private int power;
    private int blood;
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
}
