package com.aiwan.server.ramcache.persist;

import com.aiwan.server.ramcache.IEntity;

import java.io.Serializable;

/**
 * @author dengzebiao
 * 持久类元素，用户存储持久化实体的信息
 * */
public class  Element {
    private EventType type;
    private Serializable id;
    private IEntity entity;
    private Class<? extends IEntity> clz;

    /**构造函数*/
    public Element(EventType type, Serializable id, IEntity entity, Class<? extends IEntity> clz) {
        this.type = type;
        this.id = id;
        this.entity = entity;
        this.clz = clz;
    }
    public String getIdentity(){
        return clz.getName()+":"+id;
    }
    public EventType getType() {
        return type;
    }

    public Serializable getId() {
        return id;
    }

    public IEntity getEntity() {
        return entity;
    }

    public Class<? extends IEntity> getClz() {
        return clz;
    }
}
