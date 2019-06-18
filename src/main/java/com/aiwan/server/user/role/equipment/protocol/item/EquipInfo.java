package com.aiwan.server.user.role.equipment.protocol.item;

import java.io.Serializable;

/**
 * @author dengzebiao
 * @since 2019.6.18
 * 装备项
 * */
public class EquipInfo implements Serializable {
    /** 装备id
     * 0代表装备为空
     * */
    private int id;

    /** 装备位置 */
    private int position;

    public static EquipInfo valueOf(int id,int position){
        EquipInfo equipInfo = new EquipInfo();
        equipInfo.setId(id);
        equipInfo.setPosition(position);
        return equipInfo;
    }
    public int getId() {
        return id;
    }

    public EquipInfo setId(int id) {
        this.id = id;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public EquipInfo setPosition(int position) {
        this.position = position;
        return this;
    }
}
