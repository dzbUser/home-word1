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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private final static int EXECUTE_SIZE = 2;

    private ExecutorService[] executorServices;

    /**
     * 初始化
     */
    public void init() {
        executorServices = new ExecutorService[EXECUTE_SIZE];
        for (int i = 0; i < EXECUTE_SIZE; i++) {
            executorServices[i] = Executors.newSingleThreadExecutor();
        }
    }

    /**
     * 异步提交
     */
    public void asynSubmit(IEvent event) {
        this.executorServices[(int) Math.abs(event.getOwner() % EXECUTE_SIZE)].submit(() -> doSubmit(event));
    }

    /**
     * 同步提交
     *
     * @param event
     */
    @Override
    public void synSubmit(IEvent event) {
        doSubmit(event);
    }

    /**
     * 提交事件
     *
     * @param event 事件
     */
    private void doSubmit(IEvent event){
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
            receiveDefintionMap.put(receiveDefinition.getClzz(), list);
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
