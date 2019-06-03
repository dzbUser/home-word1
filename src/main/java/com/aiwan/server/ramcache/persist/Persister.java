package com.aiwan.server.ramcache.persist;

import com.aiwan.server.ramcache.IEntity;
import com.aiwan.server.ramcache.orm.Accessor;

/**
 * @author dengzebia
 * 持久化类
 * */
public interface Persister {
    /**
     * 初始化方法
     * */
    void initailize(String name,Accessor accessor);

    /**
     * 获取正在保存的element
     * @Param id
     * */
    <T extends IEntity> T get(String id);

    /**
     * 将指定元素存到队列中
     * @Param element 被添加元素
     * */
    void put(Element element);

    /**  停止更新队列并等待全部完成*/
    void shutdown();
}
