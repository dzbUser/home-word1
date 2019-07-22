package com.aiwan.server.base.event.core;

import com.aiwan.server.base.event.event.IEvent;
import com.aiwan.server.ramcache.anno.Cache;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 接收器类
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public class ReceiveDefinition {

    /**
     * 处理类
     */
    private final Object bean;

    /**
     * 方法
     */
    private final Method method;

    /**
     * 事件类
     */
    private final Class<? extends IEvent> clzz;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bean == null) ? 0 : bean.hashCode());
        result = prime * result + ((bean == null) ? 0 : method.hashCode());
        result = prime * result + ((bean == null) ? 0 : clzz.hashCode());
        return result;
    }

    private ReceiveDefinition(Object bean, Method method, Class<? extends IEvent> clzz) {
        this.bean = bean;
        this.method = method;
        this.clzz = clzz;
    }

    public static ReceiveDefinition valueOf(Object bean, Method method) {
        Class<? extends IEvent> clzz = null;
        Class<?>[] clzzs = method.getParameterTypes();
        if (clzzs.length != 1) {
            throw new IllegalArgumentException("class" + bean.getClass().getSimpleName() + "method" + method.getName()
                    + "must only has one parameter Exception");
        }
        if (!IEvent.class.isAssignableFrom(clzzs[0])) {
            throw new IllegalArgumentException("class" + bean.getClass().getSimpleName() + "method" + method.getName()
                    + "must only has one IEvent type parameter Exception");
        }
        clzz = (Class<? extends IEvent>) clzzs[0];
        return new ReceiveDefinition(bean, method, clzz);
    }

    public void invoke(IEvent event) {
        ReflectionUtils.invokeMethod(method, bean, event);
    }

    public Class<? extends IEvent> getClzz() {
        return clzz;
    }
}
