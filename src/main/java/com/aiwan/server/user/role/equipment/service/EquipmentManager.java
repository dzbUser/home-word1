package com.aiwan.server.user.role.equipment.service;

import com.aiwan.server.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.server.user.role.equipment.entity.EquipmentEntity;
import com.aiwan.server.user.role.equipment.model.EquipmentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service()
public class EquipmentManager {
    Logger logger = LoggerFactory.getLogger(EquipmentManager.class);


    private EntityCaheServiceImpl<Long, EquipmentEntity> cache;

    /** 获取背包 */
    public EquipmentModel load(Long id){
        EquipmentEntity equipmentEntity = cache.load(id);
        if (equipmentEntity == null){
            return null;
        }
        EquipmentModel equipmentModel = new EquipmentModel();
        equipmentModel.setEquipmentEntity(equipmentEntity);
        return equipmentModel;
    }

    /**
     * 装备栏实体写回
     * */
    public void writeBack(EquipmentModel equipmentModel){
        EquipmentEntity equipmentEntity = equipmentModel.getEquipmentEntity();
        cache.writeBack(equipmentEntity.getId(),equipmentEntity);
    }

    /**
     * 创建装备栏
     * */
    public void createEquipmentBar(Long rId){
        EquipmentEntity equipmentEntity = new EquipmentEntity();
        Long time = Calendar.getInstance().getTimeInMillis();
        equipmentEntity.setCreateTime(time);
        equipmentEntity.setUpdateTime(time);
        equipmentEntity.setId(rId);
        cache.writeBack(equipmentEntity.getId(),equipmentEntity);
    }
}
