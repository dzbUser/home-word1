package com.aiwan.server.role.equipment.service.impl;

import com.aiwan.server.prop.resource.Equipment;
import com.aiwan.server.prop.resource.Props;
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
    public int equip(String accountId, Long rId, int pid) {
        //获取装备栏
        EquipmentModel equipmentModel = equipmentManager.load(rId);
        //获取道具
        Equipment equipment = GetBean.getPropsManager().getEquipment(pid);
        //判断位置是否正确
        if (equipment.getPosition() > equipmentModel.getLength()){
            //位置错误
            logger.error(accountId+"中的"+rId+"装备错误");
            return -1;
        }
        EquipmentInfo equipmentInfo = equipmentModel.getEquipmentInfo();
        //旧装备的id
        int oldId = equipmentInfo.getEquipmentElements()[equipment.getPosition()].getId();
        //装备转换
        equipmentInfo.getEquipmentElements()[equipment.getPosition()].setId(pid);
        //返回装备id
        return oldId;
    }
}
