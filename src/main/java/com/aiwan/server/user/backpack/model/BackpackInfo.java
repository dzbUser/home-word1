package com.aiwan.server.user.backpack.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebia
 * @since 2019.6.4
 * 背包详细信息
 * */
public class BackpackInfo {

    /** 背包数据 */
    private Map<Integer,BackpackItem> backpackItems = new HashMap<>();

    public Map<Integer, BackpackItem> getBackpackItems() {
        return backpackItems;
    }

    public BackpackInfo setBackpackItems(Map<Integer, BackpackItem> backpackItems) {
        this.backpackItems = backpackItems;
        return this;
    }
}
