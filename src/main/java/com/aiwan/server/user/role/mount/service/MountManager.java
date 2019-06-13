package com.aiwan.server.user.role.mount.service;

import com.aiwan.server.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.mount.entity.MountEntity;
import com.aiwan.server.user.role.mount.model.MountModel;
import com.aiwan.server.user.role.mount.resource.MountResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengzebiao
 * @since 2019.6.6
 * 坐骑缓存类
 * */
@Service
public class MountManager {
    /** 坐骑缓存 */
    private EntityCaheServiceImpl<Long, MountEntity> cache;

    /** 坐骑静态资源 */
    private MountResource mountResource;

    Logger logger = LoggerFactory.getLogger(MountManager.class);

    /**坐骑信息（暂用）*/
    public static int  maxLevel = 10;


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
     * 创建坐骑
     * */
    public void createMount(Long rId){
        MountEntity mountEntity = MountEntity.getInitValue(rId);
        cache.writeBack(mountEntity.getId(),mountEntity);
    }

    public MountResource getMountResource() {
        return mountResource;
    }

    public MountManager setMountResource(MountResource mountResource) {
        this.mountResource = mountResource;
        return this;
    }
}
