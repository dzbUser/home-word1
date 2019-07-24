package com.aiwan.server.world.base.handler;

import com.aiwan.server.util.GetBean;
import com.aiwan.server.world.scenes.mapresource.MapResource;

/**
 * 副本处理类
 */
public enum DungeonType {

    /**
     * 经验副本
     */
    EXPERIENCE_HANDLER(1) {
        @Override
        public void create(long rId, MapResource mapResource) {
            GetBean.getDungeonService().createExperienceDungeon(rId, mapResource);
        }
    },

    /**
     * 通关副本
     */
    CLEARANCE_HANDLER(2) {
        @Override
        public void create(long rId, MapResource mapResource) {
            GetBean.getDungeonService().createKozanIslandDungeon(rId, mapResource);
        }
    },
    ;
    private int type;

    public static DungeonType getDungeonCreator(int type) {
        for (DungeonType dungeonType : values()) {
            if (type == dungeonType.getType()) {
                return dungeonType;
            }
        }
        throw new IllegalArgumentException("找不到副本类型,type:" + type);
    }

    DungeonType(int type) {
        this.type = type;
    }

    //创建副本
    public void create(long rId, MapResource mapResource) {

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
