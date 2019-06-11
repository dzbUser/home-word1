package com.aiwan.server.publicsystem.Initialization;

import com.aiwan.server.prop.annotation.PropType;
import com.aiwan.server.prop.model.PropUseInterface;
import com.aiwan.server.publicsystem.service.PropUserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.10
 * 道具使用初始化类
 * */
public class PropUseInitialization {

    static Logger logger = LoggerFactory.getLogger(PropUseInitialization.class);

    public static void initialization(ClassPathXmlApplicationContext applicationContext){
        logger.debug("道具使用初始化开始");
        //初始化道具类
        Map<String, Object> beansWithAnnotationMap = applicationContext.getBeansWithAnnotation(PropType.class);
        String[] names = applicationContext.getBeanNamesForAnnotation(PropType.class);
        //此处要用道具使用的注解解析出来
        for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
            Class<?> clazz= entry.getValue().getClass();
            //类中有注解
            if (clazz.isAnnotationPresent(PropType.class)){
                PropType propType = clazz.getAnnotation(PropType.class);
                //存到道具使用集合类中
                PropUserManager.putPropUse(propType.type(), (PropUseInterface) entry.getValue());
            }
        }
    }
}
