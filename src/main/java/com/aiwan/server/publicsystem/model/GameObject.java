package com.aiwan.server.publicsystem.model;

/**
 * @author dengzebiao
 * @since 2019.6.20
 * 游戏对象唯一id
 */
public abstract class GameObject {
    /** 唯一id */
    protected Long objectId;

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
}
