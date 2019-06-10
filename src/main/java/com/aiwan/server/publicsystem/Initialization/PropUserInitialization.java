package com.aiwan.server.publicsystem.Initialization;

import com.aiwan.server.prop.annotation.PropUse;
import com.aiwan.server.prop.service.PropService;
import com.aiwan.server.prop.service.impl.PropServiceImpl;
import com.aiwan.server.publicsystem.service.PropUserManager;
import com.aiwan.server.util.GetBean;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author dengzebiao
 * @since 2019.6.10
 * 道具使用初始化类
 * */
public class PropUserInitialization {

    static Logger logger = LoggerFactory.getLogger(PropUserInitialization.class);

    public static void initialization(){
        logger.debug("道具使用初始化开始");
        //获取业务类
        Class propService = GetBean.getPropService().getClass();
        //获取所有方法
        Method[] methods = propService.getMethods();
        //遍历所有方法
        for (Method method:methods){
            //获取方法上的字段注解
            Annotation[] annotations = method.getAnnotations();
            if (annotations.length == 0){
                continue;
            }

            PropUse propUse = method.getAnnotation(PropUse.class);
            int type = propUse.type();
            PropUserManager.putMethod(type,method);
        }
    }
}
