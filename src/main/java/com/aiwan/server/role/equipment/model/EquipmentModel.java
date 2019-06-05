package com.aiwan.server.role.equipment.model;

import com.aiwan.server.role.equipment.entity.EquipmentEntity;

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

//    /** 装备装备 */
//    public int equip(EquipmentElement equipmentElement){
//        if (equipmentElement.getPosition()>getLength()){
//            //装备位置越界
//            return -1;
//        }
//        //获取旧的装备id
//        int pId = equipmentEntity.getEquipmentInfo().getEquipmentElements()[equipmentElement.getPosition()].getId();
//        equipmentEntity.getEquipmentInfo().getEquipmentElements()[equipmentElement.getPosition()] = equipmentElement;
//        return pId;
//    }

    /** 获取装备长度 */
    public int getLength(){
        return equipmentEntity.getEquipmentInfo().getLength();
    }

    /** 获取装备栏 */
    public EquipmentInfo getEquipmentInfo() {
        return equipmentEntity.getEquipmentInfo();
    }
}
