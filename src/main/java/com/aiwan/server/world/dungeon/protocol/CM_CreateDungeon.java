package com.aiwan.server.world.dungeon.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 创建副本
 *
 * @author dengzbiao
 * @since 2019.7.19
 */
@ProtocolAnnotation(protocol = Protocol.CREATE_DUNGEON)
public class CM_CreateDungeon implements Serializable {

    /**
     * 创建副本的id
     */
    private int mapId;

    public static CM_CreateDungeon vulueOf(int mapId) {
        CM_CreateDungeon cm_createDungeon = new CM_CreateDungeon();
        cm_createDungeon.setMapId(mapId);
        return cm_createDungeon;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
