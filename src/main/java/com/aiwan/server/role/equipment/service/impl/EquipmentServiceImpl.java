package com.aiwan.server.role.equipment.service.impl;

import com.aiwan.server.prop.resource.Equipment;
import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.role.equipment.model.EquipmentElement;
import com.aiwan.server.role.equipment.model.EquipmentInfo;
import com.aiwan.server.role.equipment.model.EquipmentModel;
import com.aiwan.server.role.equipment.service.EquipmentManager;
import com.aiwan.server.role.equipment.service.EquipmentService;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     *
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
        //返回装备id
        return oldId;
    }

    @Override
    public String viewEquip(Long rId) {
        StringBuffer stringBuffer = new StringBuffer();
        /**
         * 获取装备栏，遍历装备栏
         * */
        EquipmentModel equipmentModel = equipmentManager.load(rId);
        Props prop;
        //获取装备栏数组
        EquipmentElement[] equipmentElements = equipmentModel.getEquipmentInfo().getEquipmentElements();
        //遍历装备栏
        for (int i =1;i<equipmentElements.length;i++){
            stringBuffer.append(EquipmentManager.equipContent[i]+":");
            if (equipmentElements[i] == null||equipmentElements[i].getId() == 0){
                stringBuffer.append("无\n");
            }else {
                prop = GetBean.getPropsManager().getProps(equipmentElements[i].getId());
                stringBuffer.append(prop.getName()+"\n");
            }
        }
        return stringBuffer.toString();
    }
}
