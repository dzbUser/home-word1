package com.aiwan.server.user.role.equipment.model;

import com.aiwan.server.prop.model.impl.Equipment;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 装备详细数据
 * */
public class EquipmentInfo {

    /** 已装备数组 */
    private Equipment[] equipments;

    public EquipmentInfo() {
        //初始化
        this.equipments = new Equipment[Equipment.length + 1];
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
            this.equipments[position] = null;
    }

    /**
     * 获取某个位置的装备
     */
    public Equipment getEquipmentByPosition(int position) {
        return this.equipments[position];
    }

    /**
     * 添加装备
     */
    public void putEquipmentByPosition(Equipment equipment) {
        this.equipments[equipment.getPosition()] = equipment;
    }
}
