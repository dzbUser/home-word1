package com.aiwan.common.ramcache.service;

import com.aiwan.common.ramcache.IEntity;

import java.io.Serializable;

public interface EntityCacheService<PK extends Comparable<PK> & Serializable,T extends IEntity<PK>> {
    /*
    * 加载指定主键的实体（同步）
    * @param id 主键
    * @return 不存在时返回null
    * */
    T load(PK id);

    /*
    * 将缓存中的指定实体写回到储存层
    * @param id 主键
    * @param entity 实体
    * */
    void writeBack(PK id,T entity);

    /*
    * 移除并删除指定实体
    * @param id 主键
    * */
    T remove(PK id);

//    PK create(T entity);

}
