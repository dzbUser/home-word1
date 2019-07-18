package com.aiwan.server.world.scenes.protocol;

import com.aiwan.server.world.scenes.model.Position;

/**
 * 怪物信息
 *
 * @author dengzebiao
 * @since 2019.7.4
 */
public class MonsterMessage {

    /**
     * 唯一id
     */
    private long objectId;

    /**
     * 资源id
     */
    private int resourceId;

    /**
     * 血量
     */
    private long blood;

    /**
     * 怪物位置
     */
    private Position position;

    public static MonsterMessage valueOf(long objectId, int resourceId, Position position, long blood) {
        MonsterMessage monsterMessage = new MonsterMessage();
        monsterMessage.setObjectId(objectId);
        monsterMessage.setPosition(position);
        monsterMessage.setResourceId(resourceId);
        monsterMessage.setBlood(blood);
        return monsterMessage;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public long getBlood() {
        return blood;
    }

    public void setBlood(long blood) {
        this.blood = blood;
    }
}
