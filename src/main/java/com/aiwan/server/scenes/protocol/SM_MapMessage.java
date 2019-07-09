package com.aiwan.server.scenes.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * @author dengzebiao
 * 返回在地图中的所有顽疾
 */
public class SM_MapMessage implements Serializable {

    /**
     * 地图id
     */
    private int map;

    /**
     * 用户本身位置
     */
    private int x;

    private int y;

    private long HP;

    private long MP;

    /**
     *角色列表
     */
    private List<RoleMessage> roleList;

    /**
     * 怪物列表
     */
    private List<MonsterMessage> monsterList;

    public static SM_MapMessage valueOf(int map, int x, int y, List<RoleMessage> roleList, List<MonsterMessage> monsterList, long HP, long MP) {
        SM_MapMessage sm_MapMessage = new SM_MapMessage();
        sm_MapMessage.setRoleList(roleList);
        sm_MapMessage.setMap(map);
        sm_MapMessage.setX(x);
        sm_MapMessage.setY(y);
        sm_MapMessage.setHP(HP);
        sm_MapMessage.setMP(MP);
        sm_MapMessage.setMonsterList(monsterList);
        return sm_MapMessage;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    public List<RoleMessage> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleMessage> roleList) {
        this.roleList = roleList;
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

    public List<MonsterMessage> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(List<MonsterMessage> monsterList) {
        this.monsterList = monsterList;
    }

    public long getHP() {
        return HP;
    }

    public void setHP(long HP) {
        this.HP = HP;
    }

    public long getMP() {
        return MP;
    }

    public void setMP(long MP) {
        this.MP = MP;
    }
}
