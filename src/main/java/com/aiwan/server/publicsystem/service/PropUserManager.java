package com.aiwan.server.publicsystem.service;

import com.aiwan.server.prop.model.PropUseInterface;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.10
 * 道具使用对应方法管理类
 * */
public class PropUserManager {
    private static Map<Integer, Method> methodMap = new HashMap<>();
    private static Map<Integer, PropUseInterface> propUseMap = new HashMap<>();

    /** 获取调用方法 */
    public static Method getMethod(int type){
        return methodMap.get(type);
    }

    /** 插入调用方法 */
    public static void putMethod(int type,Method method){
        methodMap.put(type,method);
    }

    /** 获取调用方法 */
    public static PropUseInterface getPropUse(int type){
        return propUseMap.get(type);
    }

    /** 插入调用方法 */
    public static void putPropUse(int type,PropUseInterface propUseInterface){
        propUseMap.put(type,propUseInterface);
    }
}
