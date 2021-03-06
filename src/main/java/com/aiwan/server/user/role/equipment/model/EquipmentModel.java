package com.aiwan.server.user.role.equipment.model;

import com.aiwan.server.prop.model.impl.Equipment;
import com.aiwan.server.user.role.equipment.entity.EquipmentEntity;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 装备栏业务模型
 * */
public class EquipmentModel {
    /** 装备栏实体数据 */
    private EquipmentEntity equipmentEntity;

    public EquipmentEntity getEquipmentEntity() {
        return equipmentEntity;
    }

    public EquipmentModel setEquipmentEntity(EquipmentEntity equipmentEntity) {
        this.equipmentEntity = equipmentEntity;
        return this;
    }


    /** 获取装备栏 */
    public Equipment[] getEquipmentBar() {
        return equipmentEntity.getEquipmentInfo().getEquipments();
    }

    /**
     * 设置某位置装备为空
     */
    public void setEmptyByPosition(int position) {
        equipmentEntity.getEquipmentInfo().setEmptyByPosition(position);
    }

    /**
     * 获取某个位置的装备
     */
    public Equipment getEquipmentByPosition(int position) {
        return equipmentEntity.getEquipmentInfo().getEquipmentByPosition(position);
    }

    /**
     * 设置装备
     */
    public void putEquipment(Equipment equipment) {
        equipmentEntity.getEquipmentInfo().putEquipmentByPosition(equipment);
    }

    /**
     * 获取装备数量
     */
    public int getEquipBarNum() {
        int num = 0;
        for (int i = 0; i < getEquipmentBar().length; i++) {
            if (getEquipmentBar()[i] != null) {
                num++;
            }
        }
        return num;
    }

}
