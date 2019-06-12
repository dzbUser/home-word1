package com.aiwan.client.service;

import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.11
 * 客户端信息接收的方法与类的映射
 * */
public class ClientReceiveMap {

    private static Logger logger = LoggerFactory.getLogger(ClientReceiveMap.class);

    /** 协议编号与类的映射 */
    private static Map<Method,Object> objectMap = new HashMap<>(16);

    /** 协议编号与方法的映射 */
    private static Map<Integer, Method> methodMap = new HashMap<>(16);

    /** 协议号与协议类的映射 */
    private static Map<Integer,Class<?>> classMap = new HashMap<>(16);

    /** 插入编号与类的映射 */
    public static void putObject(Method method,Object object){
        objectMap.put(method,object);
    }

    /** 根据编号获取处理类 */
    public static Object getObject(Method method){
        return objectMap.get(method);
    }

    /** 插入编号与方法的映射 */
    public static void putMethod(int status,Method method){
        methodMap.put(status,method);
    }

    /** 根据编号获取处理方法 */
    public static Method getMethod(int status){
        return methodMap.get(status);
    }

    /** 插入编号与协议类的映射 */
    public static void putClass(int status,Class<?> clazz){
        classMap.put(status,clazz);
    }

    /** 根据编号获取协议类 */
    public static Class<?> getClass(int status){
        return classMap.get(status);
    }

    /** 初始化类 */
    public static void init(ClassPathXmlApplicationContext applicationContext){
        //获取自定义为协议类的类
        Map<String, Object> beansWithAnnotationMap = applicationContext.getBeansWithAnnotation(InfoReceiveObject.class);
        for (Map.Entry<String,Object> entry:beansWithAnnotationMap.entrySet()){
            Class<?> clazz= entry.getValue().getClass();
            //遍历所有方法
            logger.debug(clazz.getName()+"加入映射");
            Method[] methods = clazz.getMethods();
            for (Method element:methods){
                //有方法注解
                if (element.isAnnotationPresent(InfoReceiveMethod.class)){
                    //获取注解状态值
                    InfoReceiveMethod infoReceiveMethod = element.getAnnotation(InfoReceiveMethod.class);
                    int status = infoReceiveMethod.status();
                    //存到映射中
                    putMethod(status,element);
                    putObject(element,entry.getValue());
                    Class[] classes = element.getParameterTypes();
                    if (classes.length>0){
                        //储存参数类型与方法的映射
                        putClass(status,classes[0]);
                    }
                }
            }
        }
    }
}
