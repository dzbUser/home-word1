package com.aiwan.server.world.scenes.model;

/**
 * 角色位置
 *
 * @author dengzebiao
 * @since 2019.7.4
 */
public class Position {
    private int x;

    private int y;

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

    public static Position valueOf(int x, int y) {
        Position position = new Position();
        position.setX(x);
        position.setY(y);
        return position;
    }
}
