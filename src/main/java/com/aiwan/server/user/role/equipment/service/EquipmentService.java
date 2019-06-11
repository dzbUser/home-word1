package com.aiwan.server.user.role.equipment.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.attributes.model.AttributeModule;
import com.aiwan.server.user.role.equipment.CM_ViewEquipBar;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 装备业务
 * */
public interface EquipmentService {
    /** 创建装备栏 */
    void createEquipmentBar(Long rId);

    /** 装备 */
    int equip(String accountId,Long rId,int pid);

    /**
     * 查看角色装备 */
    void viewEquip(CM_ViewEquipBar cm_viewEquipBar, Session session);


    /** 获取装备属性(new) */
    AttributeModule getEquipAttribute(Long rId);
}
