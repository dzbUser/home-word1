package com.aiwan.server.scenes.protocol;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author dengzebiao
 * 返回在地图中的所有顽疾
 */
public class SM_MapMessage implements Serializable {

    /**
     * 地图id
     */
    private int mapId;

    private int x;

    private int y;

    /**
     *角色列表
     */
    private List<UnitMessage> list;


    public static SM_MapMessage valueOf(int mapId, List<UnitMessage> list, int x, int y) {
        SM_MapMessage sm_MapMessage = new SM_MapMessage();
        sm_MapMessage.setMapId(mapId);
        sm_MapMessage.setList(list);
        sm_MapMessage.setX(x);
        sm_MapMessage.setY(y);
        return sm_MapMessage;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public List<UnitMessage> getList() {
        return list;
    }

    public void setList(List<UnitMessage> list) {
        this.list = list;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
