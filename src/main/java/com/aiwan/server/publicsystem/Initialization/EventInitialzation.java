package com.aiwan.server.publicsystem.Initialization;

import com.aiwan.server.util.GetBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * 事件系统初始化
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public class EventInitialzation {

    public static void init(ClassPathXmlApplicationContext applicationContext) {
        //获取所有门面类
        Map<String, Object> beansWithAnnotationMap = applicationContext.getBeansWithAnnotation(Controller.class);
        for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
            GetBean.getEventBusManager().registReceiver(entry.getValue());
        }
    }
}
