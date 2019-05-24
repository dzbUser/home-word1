package com.aiwan.common.ramcache.persist;

import com.aiwan.common.ramcache.IEntity;
import com.aiwan.common.ramcache.orm.Accessor;

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
