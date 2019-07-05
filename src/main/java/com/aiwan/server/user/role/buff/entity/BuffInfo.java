package com.aiwan.server.user.role.buff.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * buff详细数据
 *
 * @author dengzebiao
 * @since 2019.7.5
 */
public class BuffInfo {

    /**
     * 角色buff属性
     */
    private Map<Integer, BuffElement> map = new HashMap<>();

    public Map<Integer, BuffElement> getMap() {
        return map;
    }

    public void setMap(Map<Integer, BuffElement> map) {
        this.map = map;
    }

    /**
     * 获取buff
     */
    public BuffElement getBuff(int buffId) {
        return map.get(buffId);
    }

    /**
     * 移除buff
     */
    public void removeBuff(int buffId) {
        map.remove(buffId);
    }
}
