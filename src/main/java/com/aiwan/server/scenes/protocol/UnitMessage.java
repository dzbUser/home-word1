package com.aiwan.server.scenes.protocol;

import java.io.Serializable;

/**
 * 单位信息
 *
 * @author dengzebiao
 * @since 2019.7.10
 */
public class UnitMessage implements Serializable {

    /**
     * 唯一id
     */
    private long id;

    /**
     * 坐标
     */
    private int x;

    private int y;

    /**
     * 名字
     */
    private String name;

    public static UnitMessage valueOf(long id, int x, int y, String name) {
        UnitMessage unitMessage = new UnitMessage();
        unitMessage.setId(id);
        unitMessage.setName(name);
        unitMessage.setX(x);
        unitMessage.setY(y);
        return unitMessage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
