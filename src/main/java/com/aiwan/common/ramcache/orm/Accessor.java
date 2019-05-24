package com.aiwan.common.ramcache.orm;

import com.aiwan.common.ramcache.IEntity;

import java.io.Serializable;
import java.util.List;

public interface Accessor {
    /*
    * 从储存层加载指定对象实体
    * @param clz 实体来信
    * @param id 实体主键
    * @retutn 实体实例，不存在放回null
    * */
    <PK extends Serializable,T extends IEntity> T load(Class<T> clz,PK id);

    /*
     * 储存对象实体
     * @param clz 实体来信
     * @param T 实体
     * @retutn 主键对象
     * */
    <PK extends Serializable,T extends IEntity> PK sava(Class<T> clz,T entity);

    /*
     * 从储存层删除对象实体
     * @param clz 实体来信
     * @param id 实体主键
     * */
    <PK extends Serializable,T extends IEntity> void remove(Class<T> clz,PK id);

    /*
     *  更新对象实体
     * @param clz 实体来信
     * @param id 实体主键
     * */
    <PK extends Serializable,T extends IEntity> void update(Class<T> clz,final T entity);

    /*
     *  更新或存储对象实体
     * @param clz 实体来信
     * @param entity 实体对象
     * */
    <PK extends Serializable,T extends IEntity> void savaOrUpdate(Class<T> clz,T entity);

    /*
     *  批量储存
     *  @param entitys实体列表
     * */
    <PK extends Serializable,T extends IEntity> void batchSava(final List<T> entitys);

    /*
     *  批量更新
     * @param entitys实体列表
     * */
    <PK extends Serializable,T extends IEntity> void batchUpdate(final List<T> entitys);

    /*
    * 使用hql更新数据，无参数
    * */
    void bulkUpdate(String queryname);

    /*
     * 使用hql更新数据，一个参数
     * */
    void bulkUpdate(String queryname,Object value);

    /*
     * 使用hql更新数据，多个参数
     * */
    void bulkUpdate(String queryname,Object... params);

    /*
     * 使用hql获取数据，无参数
     * */
    <PK extends Serializable,T extends IEntity> List<T> find(String query);

    /*
     * 使用hql获取数据，一个参数
     * */
    <PK extends Serializable,T extends IEntity> List<T> find(String query,Object value);

    /*
     * 使用hql获取数据，多个参数
     * */
    <PK extends Serializable,T extends IEntity> List<T> find(String query,Object... params);

}
