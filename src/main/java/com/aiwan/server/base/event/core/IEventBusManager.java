package com.aiwan.server.base.event.core;

import com.aiwan.server.base.event.event.IEvent;

/**
 * 事件事务管理接口
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public interface IEventBusManager {
    public void synSubmit(final IEvent event);
}
