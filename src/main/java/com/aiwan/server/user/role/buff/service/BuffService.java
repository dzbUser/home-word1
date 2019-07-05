package com.aiwan.server.user.role.buff.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;

import java.util.Map;

/**
 * buff业务接口
 *
 * @author dengzebiao
 * @since 2019.7.5
 */
public interface BuffService {

    /**
     * 添加buff
     *
     * @param buffId
     * @param delay  延迟时间
     */
    void addBuff(Long rId, int buffId, Long delay);

    /**
     * 移除buff
     */
    void removeBuff(Long rId, int buffId);

    /**
     * 获取buff属性模块
     */
    Map<AttributeType, AttributeElement> getAttributeModule(Long rId);

    /**
     * 查看玩家身上的buff
     */
    void viewBuff(Long rId, Session session);

    /**
     * 若没有对应定时，添加定时（buff初始化时使用）
     */
    void refreshCommand(Long rId, int buffId, Long delay);
}
