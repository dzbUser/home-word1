package com.aiwan.server.publicsystem.Initialization;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.publicsystem.service.ReflectionManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author dengzebiao
 * 反射类的初始化
 * */
public class ReflectionInitialization {
    /**
     * 1.初始化协议码与协议类的映射
     * 2.初始化bean与方法的映射
     * 3.初始化协议类与方法的映射
     * */
    //初始化协议与协议类映射
    public static void InitialProtocol(ClassPathXmlApplicationContext applicationContext) {
        //获取自定义为协议类的类
        Map<String, Object> beansWithAnnotationMap = applicationContext.getBeansWithAnnotation(ProtocolAnnotation.class);
        String[] names = applicationContext.getBeanNamesForAnnotation(ProtocolAnnotation.class);
        // 此处要用反射将字段中的注解解析出来
        for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
            Class<?> clazz= entry.getValue().getClass();
            //类中有注解
            if (clazz.isAnnotationPresent(ProtocolAnnotation.class)){
                ProtocolAnnotation protocolAnnotation = clazz.getAnnotation(ProtocolAnnotation.class);
                //储存协议与协议类的映射
                ReflectionManager.putProtocolClass(protocolAnnotation.protocol(),clazz);
            }
        }

    }

    //初始化方法与Bean，方法Cm_de Class类
    public static void initialReflection(ClassPathXmlApplicationContext applicationContext) throws Exception {
        //调用协议与协议类初始化函数
        InitialProtocol(applicationContext);

        //获取所有业务类
        Map<String, Object> beansWithAnnotationMap = applicationContext.getBeansWithAnnotation(Controller.class);
        for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {

            Class<?> clazz= entry.getValue().getClass();
            //取出业务类的方法
            Method[] methods = clazz.getMethods();
            //获取业务类的bean
            Object object = applicationContext.getBean(clazz);
            for (int i= 0;i<methods.length;i++){
                //储存方法与业务类的映射
                ReflectionManager.putBean(methods[i],object);
                //获取方法的参数类型
                Class[] classes = methods[i].getParameterTypes();
                if (classes.length>0){
                    //储存参数类型与方法的映射
                    ReflectionManager.putMethod(classes[0],methods[i]);
                }
            }

        }
    }
}
