package com.aiwan.server.user.role.equipment.service.impl;

import com.aiwan.server.prop.resource.Equipment;
import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.attributes.model.AttributeItem;
import com.aiwan.server.user.role.attributes.model.AttributeModule;
import com.aiwan.server.user.role.equipment.CM_ViewEquipBar;
import com.aiwan.server.user.role.equipment.model.EquipmentElement;
import com.aiwan.server.user.role.equipment.model.EquipmentInfo;
import com.aiwan.server.user.role.equipment.model.EquipmentModel;
import com.aiwan.server.user.role.equipment.protocol.SM_EquipmentMessage;
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
        GetBean.getRoleService().putAttributeModule("equip", getEquipAttribute(rId),rId);
        //返回装备id
        return oldId;
    }

    @Override
    public void viewEquip(CM_ViewEquipBar cm_viewEquipBar, Session session) {
        StringBuffer stringBuffer = new StringBuffer();
        /**
         * 获取装备栏，遍历装备栏
         * */
        EquipmentModel equipmentModel = equipmentManager.load(cm_viewEquipBar.getrId());
        Props prop;
        //获取装备栏数组
        EquipmentElement[] equipmentElements = equipmentModel.getEquipmentInfo().getEquipmentElements();
        //遍历装备栏
        for (int i =1;i<equipmentElements.length;i++){
            stringBuffer.append(GetBean.getRoleResourceManager().getEquip(equipmentElements[i].getPosition())+":");
            if (equipmentElements[i] == null||equipmentElements[i].getId() == 0){
                stringBuffer.append("无\n");
            }else {
                prop = GetBean.getPropsManager().getProps(equipmentElements[i].getId());
                stringBuffer.append(prop.getName()+"\n");
            }
        }
        session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,stringBuffer.toString()));
    }


    @Override
    public AttributeModule getEquipAttribute(Long rId) {
        AttributeModule attributeModule = new AttributeModule();
        EquipmentModel equipmentModel = equipmentManager.load(rId);
        for (int i = 1;i<=equipmentModel.getLength();i++){
            //遍历所有装备
            int pid = equipmentModel.getEquipmentInfo().getEquipmentElements()[i].getId();
            if (pid != 0){
                //装备不为空
                Equipment equipment = GetBean.getPropsManager().getEquipment(pid);
                //获取装备属性映射
                Map<String,Integer> map = equipment.getMap();
                for (Map.Entry<String,Integer> entry:map.entrySet()) {
                    //遍历所有属性
                    AttributeItem attributeItem = attributeModule.getAttributeItem(entry.getKey());
                    if (attributeItem == null) {
                        //还未创建该类属性,添加该属性到模块中
                        attributeItem = new AttributeItem();
                        attributeItem.setName(entry.getKey());
                        attributeItem.setValue(entry.getValue());
                        attributeModule.putAttributeItem(attributeItem);
                    }else {
                        //已经存在该属性，则叠加
                        attributeItem.setValue(entry.getValue()+attributeItem.getValue());
                    }
                }
            }
        }
        return attributeModule;
    }
}
