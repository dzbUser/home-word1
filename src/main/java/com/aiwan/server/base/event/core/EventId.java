package com.aiwan.server.base.event.core;

/**
 * 事件id
 */
public enum EventId {

    /**
     * 排行榜事件
     */
    RANK_EVENT(0),
    ;

    EventId(long id) {
        this.id = id;
    }

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
