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
     * 角色位置x
     */
    private int x;

    /**
     * 角色位置y
     */
    private int y;

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

    public static RoleMessage valueOf(Long rId, String name, int x, int y) {
        RoleMessage roleMessage = new RoleMessage();
        roleMessage.setrId(rId);
        roleMessage.setName(name);
        roleMessage.setX(x);
        roleMessage.setY(y);
        return roleMessage;
    }
}
