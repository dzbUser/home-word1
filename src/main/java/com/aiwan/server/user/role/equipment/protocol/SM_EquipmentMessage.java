package com.aiwan.server.user.role.equipment.protocol;

import com.aiwan.server.prop.resource.Equipment;
import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.util.GetBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SM_EquipmentMessage implements Serializable {
    private List<Equipment> list = new ArrayList<>();

    public void put(Equipment equipment){
        list.add(equipment);
    }

    public static SM_EquipmentMessage valueOf(List<Equipment> list){
        SM_EquipmentMessage sm_equipmentMessage = new SM_EquipmentMessage();
        sm_equipmentMessage.setList(list);
        return sm_equipmentMessage;
    }

    public List<Equipment> getList() {
        return list;
    }

    public SM_EquipmentMessage setList(List<Equipment> list) {
        this.list = list;
        return this;
    }

    /** 返回String */
    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (Equipment equipment:list){
            Props prop = GetBean.getPropsManager().getProps(equipment.getId());
            stringBuffer.append(prop.getName()+"\n");
        }
        return super.toString();
    }

}
