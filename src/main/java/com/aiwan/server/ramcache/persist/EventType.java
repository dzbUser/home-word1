package com.aiwan.server.ramcache.persist;

/**
 * @author dengzebiao
 * 持久化操作类型
 * */
public enum EventType {
    /** 插入 */
    SAVE,
    /** 更新 */
    UPDATE,
    /** 删除 */
    REMOVE,
    /** 查询 */
    FIND;
}
