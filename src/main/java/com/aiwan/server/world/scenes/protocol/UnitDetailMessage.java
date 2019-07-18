package com.aiwan.server.world.scenes.protocol;

import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;

import java.io.Serializable;
import java.util.Map;

/**
 * 战斗单位详细信息
 */
public class UnitDetailMessage implements Serializable {
    /**
     * 角色id
     */
    private Long id;

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

    /**
     * 属性
     */
    private Map<AttributeType, AttributeElement> attributes;

    /**
     * 是否是怪物
     */
    private boolean isMonster;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static UnitDetailMessage valueOf(Long id, String name, int x, int y, long HP, long MP, int level, Map<AttributeType, AttributeElement> map, boolean isMonster) {
        UnitDetailMessage unitDetailMessage = new UnitDetailMessage();
        unitDetailMessage.setId(id);
        unitDetailMessage.setName(name);
        unitDetailMessage.setX(x);
        unitDetailMessage.setY(y);
        unitDetailMessage.setHP(HP);
        unitDetailMessage.setMP(MP);
        unitDetailMessage.setLevel(level);
        unitDetailMessage.setAttributes(map);
        unitDetailMessage.setMonster(isMonster);
        return unitDetailMessage;
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

    public Map<AttributeType, AttributeElement> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<AttributeType, AttributeElement> attributes) {
        this.attributes = attributes;
    }

    public boolean isMonster() {
        return isMonster;
    }

    public void setMonster(boolean monster) {
        isMonster = monster;
    }
}
