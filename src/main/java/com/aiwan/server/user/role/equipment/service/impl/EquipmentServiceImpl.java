package com.aiwan.server.user.role.equipment.service.impl;

import com.aiwan.server.prop.resource.Equipment;
import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.equipment.protocol.CM_ViewEquipBar;
import com.aiwan.server.user.role.equipment.model.EquipmentElement;
import com.aiwan.server.user.role.equipment.model.EquipmentInfo;
import com.aiwan.server.user.role.equipment.model.EquipmentModel;
import com.aiwan.server.user.role.equipment.protocol.SM_ViewEquip;
import com.aiwan.server.user.role.equipment.protocol.item.EquipInfo;
import com.aiwan.server.user.role.equipment.service.EquipmentManager;
import com.aiwan.server.user.role.equipment.service.EquipmentService;
import com.aiwan.server.util.GetBean;
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
    public int equip(String accountId, Long rId, int pid) {
        //获取装备栏
        EquipmentModel equipmentModel = equipmentManager.load(rId);
        //获取装备
        Equipment equipment = GetBean.getPropsManager().getEquipment(pid);
        //判断位置是否正确
        if (equipment == null||equipment.getPosition() > equipmentModel.getLength()){
            //位置错误
            logger.error(accountId+"中的"+rId+"装备错误");
            return -1;
        }
        EquipmentInfo equipmentInfo = equipmentModel.getEquipmentInfo();

        //旧装备的id,0表示装备栏无装备
        int oldId = equipmentInfo.getEquipmentElements()[equipment.getPosition()].getId();
        //装备转换
        equipmentInfo.getEquipmentElements()[equipment.getPosition()].setId(pid);
        equipmentManager.writeBack(equipmentModel);

        //修改人物属性
        GetBean.getRoleService().putAttributeModule("equip", getEquipAttributes(rId),rId);
        //返回装备id
        return oldId;
    }

    @Override
    public void viewEquip(CM_ViewEquipBar cm_viewEquipBar, Session session) {
        /**
         * 获取装备栏，遍历装备栏
         * */
        EquipmentModel equipmentModel = equipmentManager.load(cm_viewEquipBar.getrId());
        Props prop;
        //获取装备栏数组
        EquipmentElement[] equipmentElements = equipmentModel.getEquipmentInfo().getEquipmentElements();
        //创建列表
        List<EquipInfo> list = new ArrayList<EquipInfo>();
        //遍历装备栏
        for (int i =1;i<equipmentElements.length;i++){
            EquipInfo equipInfo = EquipInfo.valueOf(equipmentElements[i].getId(),equipmentElements[i].getPosition());
            list.add(equipInfo);
        }
        SM_ViewEquip sm_viewEquip = SM_ViewEquip.valueOf(list);
        session.messageSend(SMToDecodeData.shift(StatusCode.VIEWEQUIP,sm_viewEquip));
    }

    @Override
    public Map<AttributeType, AttributeElement> getEquipAttributes(Long rId) {
        //创建属性列表
        Map<AttributeType, AttributeElement> elementHashMap = new HashMap<AttributeType, AttributeElement>();
        EquipmentModel equipmentModel = equipmentManager.load(rId);
        for (int i = 1;i<=equipmentModel.getLength();i++){
            //遍历所有装备
            int pid = equipmentModel.getEquipmentInfo().getEquipmentElements()[i].getId();
            if (pid != 0){
                //装备不为空
                Equipment equipment = GetBean.getPropsManager().getEquipment(pid);
                //获取装备属性映射
                Map<Integer,Integer> map = equipment.getMap();
                for (Map.Entry<Integer,Integer> entry:map.entrySet()) {
                    //获取属性类型
                    AttributeType attributeType = AttributeType.getType(entry.getKey());
                    //遍历所有属性
                    AttributeElement attributeElement = elementHashMap.get(attributeType);
                    if (attributeElement == null) {
                        //还未创建该类属性,添加该属性到模块中
                        attributeElement = AttributeElement.valueOf(attributeType,entry.getValue());
                        elementHashMap.put(attributeType,attributeElement);
                    }else {
                        //已经存在该属性，则叠加
                        attributeElement.setValue(entry.getValue()+attributeElement.getValue());
                    }
                }
            }
        }

        return elementHashMap;
    }

}
