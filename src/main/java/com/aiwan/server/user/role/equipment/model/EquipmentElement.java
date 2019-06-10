package com.aiwan.server.user.role.equipment.model;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 装备模型
 * */
public class EquipmentElement {
    /** 装备位置 */
    private int position;

    /** 道具id */
    private int id;

    public int getPosition() {
        return position;
    }

    public EquipmentElement setPosition(int position) {
        this.position = position;
        return this;
    }

    public int getId() {
        return id;
    }

    public EquipmentElement setId(int id) {
        this.id = id;
        return this;
    }
}
