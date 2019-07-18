package com.aiwan.server.world.scenes.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

/**
 * 查看场景内所有角色
 *
 * @author dengzebiao
 * @since 2019.7.10
 */
@ProtocolAnnotation(protocol = Protocol.VIEW_ALLUNIT_INMAP)
public class CM_ViewAllUnitInMap {

    private int mapId;

    public static CM_ViewAllUnitInMap valueOf(int mapId) {
        CM_ViewAllUnitInMap cm_viewAllUnitInMap = new CM_ViewAllUnitInMap();
        cm_viewAllUnitInMap.setMapId(mapId);
        return cm_viewAllUnitInMap;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
