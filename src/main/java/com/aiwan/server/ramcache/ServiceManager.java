package com.aiwan.server.ramcache;

import com.aiwan.server.ramcache.anno.Cache;
import com.aiwan.server.ramcache.service.EntityCacheService;
import com.aiwan.server.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.server.util.GetBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * 业务管理类
 * */
public class ServiceManager {
    //存储所有创建的缓存
    public static Map<Class,EntityCaheServiceImpl> cacheMaps = new HashMap<>();
    /**
     * 创建缓存
     * */
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
