package com.aiwan.server.role.mount.service;

import com.aiwan.server.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.server.role.mount.entity.MountEntity;
import com.aiwan.server.role.mount.model.MountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author dengzebiao
 * @since 2019.6.6
 * 坐骑缓存类
 * */
@Service
public class MountManager {
    /** 坐骑缓存 */
    private EntityCaheServiceImpl<Long, MountEntity> cache;

    Logger logger = LoggerFactory.getLogger(MountManager.class);

    /**坐骑信息（暂用）*/
    public static String[] mountName ;
    public static int  maxLevel = 10;
    static {
        mountName = new String[maxLevel+1];
        mountName[1] = "黑马";
        mountName[2] = "白马";
        mountName[3] = "赤兔马";
        mountName[4] = "野猪王";
        mountName[5] = "狼王";
        mountName[6] = "狮王";
        mountName[7] = "白虎王";
        mountName[8] = "龙鱼";
        mountName[9] = "饕餮";
        mountName[10] = "金龙";
    }


    /** 获取坐骑 */
    public MountModel load(Long id){
        MountEntity mountEntity = cache.load(id);
        if (mountEntity == null){
            return null;
        }
        MountModel mountModel = new MountModel();
        mountModel.setMountEntity(mountEntity);
        return mountModel;
    }

    /**
     * 装备栏实体写回
     * */
    public void writeBack(MountModel mountModel){
        MountEntity mountEntity = mountModel.getMountEntity();
        cache.writeBack(mountEntity.getId(),mountEntity);
    }

    /**
     * 创建装备栏
     * */
    public void createMount(Long rId){
        MountEntity mountEntity = MountEntity.getInitValue(rId);
        cache.writeBack(mountEntity.getId(),mountEntity);
    }
}
