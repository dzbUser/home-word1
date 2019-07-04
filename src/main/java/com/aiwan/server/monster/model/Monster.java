package com.aiwan.server.monster.model;

import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.publicsystem.model.GameObject;
import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.IDUtil;

import java.util.Map;

/**
 * 怪物单元
 *
 * @author dengzebiao
 * @since 2019.7.4
 */
public class Monster extends GameObject {

    /**
     * 怪物资源id
     */
    private int resourceId;

    /**
     * 坐标
     */
    private Position position;

    private long blood;

    /**
     * 获取静态资源
     */
    public MonsterResource getResource() {
        return GetBean.getMonsterManager().getResourceById(resourceId);
    }

    public Monster(int resourceId, Position position) {
        this.resourceId = resourceId;
        this.position = position;
        this.blood = getResource().getAttributeMap().get(AttributeType.BLOOD).getValue();
        setObjectId(IDUtil.getId());
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
