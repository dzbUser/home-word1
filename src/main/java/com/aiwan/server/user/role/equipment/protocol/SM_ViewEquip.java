package com.aiwan.server.user.role.equipment.protocol;

import com.aiwan.server.user.role.equipment.protocol.item.EquipInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author dengzebiao
 * @since 2019.6.18
 * 装备查看协议
 * */
public class SM_ViewEquip implements Serializable {
    private List<EquipInfo> list;

    public static SM_ViewEquip valueOf(List<EquipInfo> list){
        SM_ViewEquip sm_viewEquip = new SM_ViewEquip();
        sm_viewEquip.setList(list);
        return sm_viewEquip;
    }
    public List<EquipInfo> getList() {
        return list;
    }

    public SM_ViewEquip setList(List<EquipInfo> list) {
        this.list = list;
        return this;
    }
}
