package com.aiwan.server.ramcache;

import java.io.Serializable;

public interface IEntity <PK extends Serializable& Comparable<PK>>{
    /**获取实体id*/
    public PK getId();
    /**序列化方法*/
    public boolean serialize();
    /** 反序列化方法*/
    public boolean unserialize();

    /** 初始化方法 */
    void init();
}
