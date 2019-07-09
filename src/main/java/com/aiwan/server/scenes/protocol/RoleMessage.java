package com.aiwan.server.scenes.protocol;


import java.io.Serializable;

/**
 * 玩家信息
 *
 * @author dengzebiao
 */
public class RoleMessage implements Serializable {

    /**
     * 角色id
     */
    private Long rId;

    /**
     * 角色名字
     */
    private String name;

    /**
     * 等级
     */
    private int level;

    /**
     * 角色位置x
     */
    private int x;

    /**
     * 角色位置y
     */
    private int y;

    /**
     * 血量
     */
    private long HP;

    /**
     * 魔法值
     */
    private long MP;


    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static RoleMessage valueOf(Long rId, String name, int x, int y, long HP, long MP, int level) {
        RoleMessage roleMessage = new RoleMessage();
        roleMessage.setrId(rId);
        roleMessage.setName(name);
        roleMessage.setX(x);
        roleMessage.setY(y);
        roleMessage.setHP(HP);
        roleMessage.setMP(MP);
        roleMessage.setLevel(level);
        return roleMessage;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
