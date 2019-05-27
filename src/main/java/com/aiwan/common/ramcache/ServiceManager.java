package com.aiwan.common.ramcache;

import com.aiwan.common.ramcache.anno.Cache;
import com.aiwan.common.ramcache.service.EntityCacheService;
import com.aiwan.common.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.util.GetBean;

public class ServiceManager {
    public EntityCacheService getEntityCacheService(Class<? extends IEntity> clz){
        if (clz.isAnnotationPresent(Cache.class)) {//有注解
            Cache cached = clz.getAnnotation(Cache.class);
            int maxmum = cached.maxmum();
            //初始化
            EntityCaheServiceImpl entityCaheService = new EntityCaheServiceImpl();
            entityCaheService.initailize(clz.getName(),GetBean.getAccessor(),clz,maxmum);
            return entityCaheService;
        }
        return null;
    }
}
