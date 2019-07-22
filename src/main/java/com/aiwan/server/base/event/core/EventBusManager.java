package com.aiwan.server.base.event.core;

import com.aiwan.server.base.event.anno.ReceiverAnno;
import com.aiwan.server.base.event.event.IEvent;
import com.aiwan.server.publicsystem.annotation.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 事件事务管理实现类
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
@Component
public class EventBusManager implements IEventBusManager {

    private static final Logger logger = LoggerFactory.getLogger(EventBusManager.class);

    private Map<Class<? extends IEvent>, List<ReceiveDefinition>> receiveDefintionMap = new HashMap<>();


    @Override
    public void synSubmit(IEvent event) {
        List<ReceiveDefinition> list = receiveDefintionMap.get(event.getClass());
        if (list == null || list.isEmpty()) {
            logger.warn("no any receiver found for event:{}", event.getClass());
            return;
        }
        for (ReceiveDefinition receiveDefinition : list) {
            try {
                receiveDefinition.invoke(event);
            } catch (Exception e) {
                String message;
                if (event == null) {
                    message = "event is null";
                } else if (event.getClass() == null) {
                    message = "event.getClass() is null";
                } else {
                    message = event.getClass().getSimpleName();
                }
                logger.error("event handler exception:{}", message);
            }
        }
    }

    /**
     * 注册事件
     *
     * @param bean   事件监听类
     * @param method 事件监听方法
     */
    public void registReceiver(Object bean, Method method) {
        ReceiveDefinition receiveDefinition = ReceiveDefinition.valueOf(bean, method);
        List<ReceiveDefinition> list = receiveDefintionMap.get(receiveDefinition.getClzz());
        if (list == null) {
            list = new CopyOnWriteArrayList<>();
        }
        if (!list.contains(receiveDefinition)) {
            //没有该类
            list.add(receiveDefinition);
        }
    }

    /**
     * 注册事件
     *
     * @param bean 事件监听类
     */
    public void registReceiver(Object bean) {
        Class<?> clz = bean.getClass();
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ReceiverAnno.class)) {
                registReceiver(bean, method);
            }
        }
    }
}
