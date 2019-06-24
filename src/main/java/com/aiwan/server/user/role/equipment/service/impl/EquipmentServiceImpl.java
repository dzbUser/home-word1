package com.aiwan.server.user.role.equipment.service.impl;

import com.aiwan.server.prop.model.impl.Equipment;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.equipment.model.EquipmentModel;
import com.aiwan.server.user.role.equipment.protocol.SM_ViewEquip;
import com.aiwan.server.user.role.equipment.protocol.item.EquipInfo;
import com.aiwan.server.user.role.equipment.service.EquipmentManager;
import com.aiwan.server.user.role.equipment.service.EquipmentService;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 装备业务实现类
 * */
@Service
public class EquipmentServiceImpl implements EquipmentService {
    Logger logger = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    @Autowired
    private EquipmentManager equipmentManager;

    @Override
    public void createEquipmentBar(Long rId) {
        //新建装备栏
        equipmentManager.createEquipmentBar(rId);
    }

    @Override
    /**
     *装备
     * */
    public Equipment equip(String accountId, Long rId, Equipment equipment) {
        //获取装备栏
        EquipmentModel equipmentModel = equipmentManager.load(rId);
        //旧装备
        Equipment oldEquipment = equipmentModel.getEquipmentByPosition(equipment.getPosition());
        //装备转换
        equipmentModel.putEquipment(equipment);
        equipmentManager.writeBack(equipmentModel);
        //修改人物属性
        GetBean.getRoleService().updateAttributeModule("equip", getEquipAttributes(rId), rId);
        logger.info(accountId + "装备" + equipment.getPropsResource().getName() + "成功");
        //返回装备id
        return oldEquipment;
    }

    @Override
    public void viewEquip(String accountId, Long rId, Session session) {
        /**
         * 获取装备栏，遍历装备栏
         * */

        logger.info("{}：查看装备信息", rId);
        EquipmentModel equipmentModel = equipmentManager.load(rId);
        PropsResource prop;
        //获取装备栏数组
        Equipment[] equipments = equipmentModel.getEquipmentBar();
        //创建列表
        List<EquipInfo> list = new ArrayList<EquipInfo>();
        //遍历装备栏
        for (int i = 1; i < equipments.length; i++) {
            EquipInfo equipInfo;
            if (equipments[i] == null) {
                equipInfo = EquipInfo.valueOf(0, i);
            } else {
                equipInfo = EquipInfo.valueOf(equipments[i].getResourceId(), i);
            }
            list.add(equipInfo);
        }
        SM_ViewEquip sm_viewEquip = SM_ViewEquip.valueOf(list);
        session.messageSend(SMToDecodeData.shift(StatusCode.VIEWEQUIP,sm_viewEquip));
    }

    @Override
    public Map<AttributeType, AttributeElement> getEquipAttributes(Long rId) {
        logger.info(rId+":获取属性");
        //创建属性列表
        Map<AttributeType, AttributeElement> elementHashMap = new HashMap<AttributeType, AttributeElement>();
        EquipmentModel equipmentModel = equipmentManager.load(rId);
        //获取装备数组
        // TODO: 2019/6/24 是否会被修改
        Equipment[] equipments = equipmentModel.getEquipmentBar();
        for (Equipment equipment : equipments) {
            //遍历所有装备
            if (equipment != null) {
                //装备不为空
                //获取装备属性映射
                Map<AttributeType, AttributeElement> map = equipment.getAttribute();
                for (Map.Entry<AttributeType, AttributeElement> entry : map.entrySet()) {
                    //遍历所有属性
                    AttributeElement attributeElement = elementHashMap.get(entry.getKey());
                    if (attributeElement == null) {
                        //还未创建该类属性,添加该属性到模块中
                        attributeElement = AttributeElement.valueOf(entry.getKey(), entry.getValue().getValue());
                        elementHashMap.put(entry.getKey(), attributeElement);
                    }else {
                        //已经存在该属性，则叠加
                        attributeElement.setValue(entry.getValue().getValue() + attributeElement.getValue());
                    }
                }
            }
        }

        return elementHashMap;
    }

    /**
     * 卸装备
     */
    @Override
    public void unload(String accountId, Long rid, int position, Session session) {
        if (position < 1 || position > Equipment.length) {
            session.sendPromptMessage(PromptCode.UNLOADEQUIPFAIL, "");
            return;
        }
        logger.info(rid + ":卸装备");
        //获取装备
        EquipmentModel equipmentModel = equipmentManager.load(rid);
        Equipment equipment = equipmentModel.getEquipmentByPosition(position);
        if (equipment == null) {
            //装备位置为空
            session.sendPromptMessage(PromptCode.EQUIPEMPTY, "");
        } else {
            //背包获取装备
            equipmentModel.setEmptyByPosition(position);
            equipmentManager.writeBack(equipmentModel);
            GetBean.getBackpackService().obtainProp(accountId, equipment);
            //重新计算战斗力
            GetBean.getRoleService().updateAttributeModule("equip", getEquipAttributes(rid), rid);
            session.sendPromptMessage(PromptCode.UNLOADEQUIPSUCCESS, "");
        }

    }


}
