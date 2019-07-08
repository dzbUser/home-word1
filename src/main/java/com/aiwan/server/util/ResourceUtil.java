package com.aiwan.server.util;

import com.aiwan.server.publicsystem.annotation.Resource;
import com.aiwan.server.publicsystem.annotation.Static;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.7.1
 * 静态资源工具类
 */
public class ResourceUtil {

    /**
     * 获取静态资源路径
     *
     * @param cls 资源类
     */
    public static String getResourcePath(Class<?> cls) {
        Resource resource = cls.getAnnotation(Resource.class);
        return resource.value();
    }

    /**
     * 初始化静态资源
     */
    public static void initResource(ClassPathXmlApplicationContext applicationContext) {
        //获取Component类
        //获取自定义为协议类的类
        Map<String, Object> beansWithAnnotationMap = applicationContext.getBeansWithAnnotation(Component.class);
        // 此处要用反射将字段中的注解解析出来
        for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            //获取所有域
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                //遍历所有类
                if (field.isAnnotationPresent(Static.class)) {
                    //有资源注释
                    String initMethod = field.getAnnotation(Static.class).initializeMethodName();
                    try {
                        Method method = clazz.getDeclaredMethod(initMethod);
                        //反射调用资源初始化方法
                        method.setAccessible(true);
                        method.invoke(entry.getValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
