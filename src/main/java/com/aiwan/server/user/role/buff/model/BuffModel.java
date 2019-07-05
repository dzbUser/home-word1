package com.aiwan.server.user.role.buff.model;

import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.attributes.model.ImmutableAttributeElement;
import com.aiwan.server.user.role.buff.entity.BuffElement;
import com.aiwan.server.user.role.buff.entity.BuffEntity;
import com.aiwan.server.user.role.buff.resource.BuffResource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * Buff模型
 */
public class BuffModel {

    private BuffEntity buffEntity;

    public BuffEntity getBuffEntity() {
        return buffEntity;
    }

    public void setBuffEntity(BuffEntity buffEntity) {
        this.buffEntity = buffEntity;
    }

    /**
     * 是否存在该buff
     */
    public boolean isExistBuff(int buffId) {
        BuffElement buffElement = getBuffEntity().getBuff(buffId);
        //获取当前时间戳
        Long currentTime = System.currentTimeMillis();
        if (buffElement == null || buffElement.getOverTime() <= currentTime) {
            getBuffEntity().removeBuff(buffId);
            return false;
        }
        return true;
    }

    /**
     * 添加buff
     */
    public void putBuff(int buffId, Long overTime) {
        BuffElement buffElement = BuffElement.valueOf(buffId, overTime);
        getBuffEntity().putBuff(buffElement);
    }

    /**
     * 移除buff
     */
    public void removeBuff(int buffId) {
        getBuffEntity().removeBuff(buffId);
    }

    /**
     * 重置时间
     */
    public void resetTime(int buffId, long overTime) {
        BuffElement buffElement = getBuffEntity().getBuffInfo().getMap().get(buffId);
        buffElement.setOverTime(overTime);
    }


    /**
     * 获取结束时间
     */
    public long getOverTime(int buffId) {
        return getBuffEntity().getBuff(buffId).getOverTime();
    }

    /**
     * 获取属性模块
     */
    public Map<AttributeType, AttributeElement> getAttributeModule() {
        /*
         * 1.便利buff属性，叠加属性
         * */
        Map<AttributeType, AttributeElement> attributeModule = new HashMap<>();
        for (BuffElement buffElement : buffEntity.getBuffInfo().getMap().values()) {
            BuffResource buffResource = buffElement.getBuffResource();
            Map<AttributeType, AttributeElement> elementMap = ImmutableAttributeElement.wrapper(buffResource.getAttributeMap());
            for (Map.Entry<AttributeType, AttributeElement> elementEntry : elementMap.entrySet()) {
                //叠加buff属性
                AttributeElement attributeElement = attributeModule.get(elementEntry.getKey());
                if (attributeElement == null) {
                    //还没有该属性
                    attributeElement = AttributeElement.valueOf(elementEntry.getKey(), elementEntry.getValue().getValue());
                    attributeModule.put(elementEntry.getKey(), attributeElement);
                } else {
                    //已有属性
                    attributeElement.setValue(attributeElement.getValue() + elementEntry.getValue().getValue());
                }
            }
        }

        return attributeModule;
    }

}
