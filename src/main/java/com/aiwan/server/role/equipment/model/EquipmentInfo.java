package com.aiwan.server.role.equipment.model;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 装备详细数据
 * */
public class EquipmentInfo {
    /** 装备栏长度 */
    private int length = 3;

    /** 已装备数组 */
    private EquipmentElement[] equipmentElements;

    public EquipmentInfo() {
        //初始化
        this.equipmentElements = new EquipmentElement[length+1];
        for (int i = 1;i<=length;i++){
            equipmentElements[i] = new EquipmentElement();
            equipmentElements[i].setId(0);
            equipmentElements[i].setPosition(i);
        }
    }

    public int getLength() {
        return length;
    }

    public EquipmentInfo setLength(int length) {
        this.length = length;
        return this;
    }

    public EquipmentElement[] getEquipmentElements() {
        return equipmentElements;
    }

    public EquipmentInfo setEquipmentElements(EquipmentElement[] equipmentElements) {
        this.equipmentElements = equipmentElements;
        return this;
    }
}
