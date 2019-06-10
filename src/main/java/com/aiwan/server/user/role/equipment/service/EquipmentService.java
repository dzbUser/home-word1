package com.aiwan.server.user.role.equipment.service;

import com.aiwan.server.user.role.attributes.model.AttributeModule;
/**
 * @author dengzebiao
 * @since 2019.6.5
 * 装备业务
 * */
public interface EquipmentService {
    /** 创建装备栏 */
    public void createEquipmentBar(Long rId);

    /** 装备 */
    public int equip(String accountId,Long rId,int pid);

    /**
     * @param rId 角色id
     * 查看角色装备 */
    public String viewEquip(Long rId);


    /** 获取装备属性(new) */
    AttributeModule getEquipAttribute(Long rId);
}
