package com.aiwan.server.user.role.equipment.model;

import com.aiwan.server.prop.model.PropsType;
import com.aiwan.server.prop.model.impl.Equipment;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 装备详细数据
 * */
public class EquipmentInfo {
    /** 装备栏长度 */
    private int length = Equipment.length;

    /** 已装备数组 */
    private Equipment[] equipments;

    public EquipmentInfo() {
        //初始化
        this.equipments = new Equipment[length + 1];
    }

    public int getLength() {
        return length;
    }

    public EquipmentInfo setLength(int length) {
        this.length = length;
        return this;
    }

    public Equipment[] getEquipments() {
        return equipments;
    }

    public void setEquipments(Equipment[] equipments) {
        this.equipments = equipments;
    }

    /**
     * 设置某位置装备为空
     */
    public void setEmptyByPosition(int position) {
        if (position > 0 && position <= length) {
            this.equipments[position] = null;
        }
    }

    /**
     * 获取某个位置的装备
     */
    public Equipment getEquipmentByPosition(int position) {
        if (position > 0 && position <= length) {
            return this.equipments[position];
        }
        return null;
    }

    /**
     * 设置装备
     */
    public void putEquipmentByPosition(Equipment equipment) {
        this.equipments[equipment.getPosition()] = equipment;
    }
}
