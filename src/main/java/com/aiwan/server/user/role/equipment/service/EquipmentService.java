package com.aiwan.server.user.role.equipment.service;

import com.aiwan.server.prop.model.impl.Equipment;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.equipment.protocol.CM_ViewEquipBar;

import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 装备业务
 * */
public interface EquipmentService {
    /** 创建装备栏 */
    void createEquipmentBar(Long rId);

    /** 装备 */
    Equipment equip(String accountId, Long rId, Equipment equipment);

    /**
     * 查看角色装备 */
    void viewEquip(CM_ViewEquipBar cm_viewEquipBar, Session session);


    /** 获取装备属性 */
    Map<AttributeType, AttributeElement> getEquipAttributes(Long rId);
}
