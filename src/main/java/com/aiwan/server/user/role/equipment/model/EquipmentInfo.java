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
    private Equipment[] Equipments;

    public EquipmentInfo() {
        //初始化
        this.Equipments = new Equipment[length + 1];
        for (int i = 1;i<=length;i++){
            Equipments[i] = new Equipment();
            Equipments[i].setId(PropsType.emptyId);
        }
    }

    public int getLength() {
        return length;
    }

    public EquipmentInfo setLength(int length) {
        this.length = length;
        return this;
    }

    public Equipment[] getEquipments() {
        return Equipments;
    }

    public void setEquipments(Equipment[] equipments) {
        Equipments = equipments;
    }
}
