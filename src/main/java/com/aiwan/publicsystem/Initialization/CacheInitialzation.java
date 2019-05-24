package com.aiwan.publicsystem.Initialization;

import com.aiwan.common.ramcache.IEntity;
import com.aiwan.common.ramcache.ServiceManager;
import com.aiwan.common.ramcache.service.impl.EntityCaheServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class CacheInitialzation {
    private static final Logger logger = LoggerFactory.getLogger(CacheInitialzation.class);
    public static void init(ClassPathXmlApplicationContext applicationContext) throws NoSuchFieldException, IllegalAccessException {
        /**
         * 1.获取所有Service，bean
         * 2.查看ServiceBean的属性
         * 3.若有EntityCacheService属性，则创建cache，并注入
         * */
        ServiceManager serviceManager = new ServiceManager();
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~第一步~~~~~~~~~~~~~~~~~~~~~~~~~
        Map<String, Object> beansWithAnnotationMap = applicationContext.getBeansWithAnnotation(Service.class);
        //~~~~~~~~~~~~~~~~~~~~~~~~第二部~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
            Class<?> clazz= entry.getValue().getClass();
            //取出业务类的属性
            Field[] fields = clazz.getDeclaredFields();
            for (Field field:fields){
                if (field.getName().equals("cache")){
                    logger.debug(field.getName()+"开始创建缓存");
                    //获取缓存类型中的实体类型
                    Type type = field.getGenericType();
                    Type[] types = ((ParameterizedType)type).getActualTypeArguments();
                    Class<? extends IEntity> clz = (Class<? extends IEntity>) types[1];
                    if (field!=null){
                        //~~~~~~~~~~~~~~第三步~~~~~~~~~~~~~~~~
                        field.setAccessible(true);
                        field.set(entry.getValue(),serviceManager.getEntityCacheService(clz));
                    }
                }
            }

        }
    }
}
