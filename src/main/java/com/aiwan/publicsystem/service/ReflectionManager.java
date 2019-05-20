package com.aiwan.publicsystem.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射管理
 * */
public class ReflectionManager {
    //储存协议与协议类的映射
    private static Map<Integer,Class<?>> protocolMaps = new HashMap<>();
    //储存bean与mathod
    private static Map<Method,Object> beanMaps = new HashMap<>();
    //储存Class类型与方法的映射
    private static Map<Class<?>,Method> methodMaps = new HashMap<>();

    //获取协议类
    public static Class<?> getProtocolClass(Integer type){
        return protocolMaps.get(type);
    }
    //存储协议类
    public static void putProtocolClass(Integer type,Class<?> protocolClass){
        protocolMaps.put(type,protocolClass);
    }
    //获取反射bean
    public static Object getBean(Method method){
        return beanMaps.get(method);
    }
    //存储bean
    public static void putBean(Method method,Object object){
        beanMaps.put(method,object);
    }

    //获取反射方法
    public static Method getMethod(Class clazz){
        return methodMaps.get(clazz);
    }
    //存储反射方法
    public static void putMethod(Class clazz,Method method){
        methodMaps.put(clazz,method);
    }
}
