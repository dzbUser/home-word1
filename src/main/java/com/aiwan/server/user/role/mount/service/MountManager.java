package com.aiwan.server.user.role.mount.service;

import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.ramcache.service.impl.EntityCacheServiceImpl;
import com.aiwan.server.user.role.mount.entity.MountEntity;
import com.aiwan.server.user.role.mount.model.MountModel;
import com.aiwan.server.user.role.mount.resource.MountResource;
import com.aiwan.server.util.ExcelUtil;
import com.aiwan.server.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author dengzebiao
 * @since 2019.6.6
 * 坐骑缓存类
 * */
@Manager
public class MountManager {
    /** 坐骑缓存 */
    private EntityCacheServiceImpl<Long, MountEntity> cache;

    /** 坐骑静态资源 */
    @Static(initializeMethodName = "initMountResource")
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

    private void initMountResource() {
        //获取静态资源路径
        String path = ResourceUtil.getResourcePath(MountResource.class);
        logger.info("初始化任务静态资源");
        List<MountResource> list = null;
        try {
            list = ExcelUtil.analysisWithRelativePath(path, MountResource.class);
        } catch (IllegalAccessException e) {
            logger.error(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            logger.error(e.getLocalizedMessage());
        }
        list.get(0).init();
        setMountResource(list.get(0));
    }
}
