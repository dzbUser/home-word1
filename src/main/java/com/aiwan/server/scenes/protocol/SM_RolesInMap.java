package com.aiwan.server.scenes.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * @author dengzebiao
 * 返回在地图中的所有顽疾
 */
public class SM_RolesInMap implements Serializable {

    /**
     * 地图id
     */
    private int map;

    /**
     * 用户本身位置
     */
    private int x;

    private int y;

    /**
     * 用户例子
     */
    private List<RoleMessage> list;

    public static SM_RolesInMap valueOf(int map, int x, int y, List<RoleMessage> list) {
        SM_RolesInMap sm_rolesInMap = new SM_RolesInMap();
        sm_rolesInMap.setList(list);
        sm_rolesInMap.setMap(map);
        sm_rolesInMap.setX(x);
        sm_rolesInMap.setY(y);
        return sm_rolesInMap;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    public List<RoleMessage> getList() {
        return list;
    }

    public void setList(List<RoleMessage> list) {
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
