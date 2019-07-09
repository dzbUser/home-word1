package com.aiwan.server.scenes.fight.model.pvpunit;

import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.buff.effect.AbstractBuffEffect;
import com.aiwan.server.user.role.buff.effect.BuffType;

import java.util.HashMap;
import java.util.Map;

/**
 * PvP基础单位
 *
 * @author dengzebiao
 * @since 2019.7.9
 */
public abstract class BaseUnit {

    /**
     * 唯一id
     */
    private Long id;

    /**
     * 血量
     */
    private long hp;

    /**
     * 魔法值
     */
    private long mp;

    /**
     * 位置
     */
    private Position position;

    /**
     * 是否死亡状态
     */
    private boolean isDeath = false;

    /**
     * 是否是怪物
     */
    private boolean isMonster;

    /**
     * 角色所属地图
     */
    private int mapId;

    /**
     * 名字
     */
    private String name;

    /**
     * 等级
     */
    private int level;

    /**
     * 最终属性
     */
    private Map<AttributeType, AttributeElement> finalAttribute = new HashMap<>();


    /**
     * buff列表
     */
    private Map<BuffType, AbstractBuffEffect> buff = new HashMap<>();


    /**
     * 扣除血量
     */
    public void deduceHP(Long attackId, long hurt) {
        long finalHP = hp - hurt;
        setHp(finalHP);
        if (finalHP <= 0) {
            //战斗单位死亡，出发死亡机制
            death(attackId);
        }
    }

    /**
     * 获取最大HP
     */
    public long getMaxHp() {
        return finalAttribute.get(AttributeType.BLOOD).getValue();
    }

    public long getMaxMp() {
        return finalAttribute.get(AttributeType.MAGIC).getValue();
    }

    /**
     * 出发死亡机制
     */
    protected abstract void death(Long attackId);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getHp() {
        return hp;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    public long getMp() {
        return mp;
    }

    public void setMp(long mp) {
        this.mp = mp;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Map<AttributeType, AttributeElement> getFinalAttribute() {
        return finalAttribute;
    }

    public void setFinalAttribute(Map<AttributeType, AttributeElement> finalAttribute) {
        this.finalAttribute = finalAttribute;
    }

    public Map<BuffType, AbstractBuffEffect> getBuff() {
        return buff;
    }

    public void setBuff(Map<BuffType, AbstractBuffEffect> buff) {
        this.buff = buff;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public void setDeath(boolean death) {
        isDeath = death;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public boolean isMonster() {
        return isMonster;
    }

    public void setMonster(boolean monster) {
        isMonster = monster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
