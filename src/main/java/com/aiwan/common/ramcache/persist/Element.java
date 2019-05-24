package com.aiwan.common.ramcache.persist;

import com.aiwan.common.ramcache.IEntity;

import java.io.Serializable;

public class Element {
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
