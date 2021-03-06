package com.aiwan.server.user.role.player.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.player.protocol.CM_RoleMessage;
import com.aiwan.server.user.account.protocol.CM_CreateRole;

import java.util.Map;

/**
 * @author dengzebiao
 * 角色业务类
 * */
public interface RoleService {
    /**
     * 创建角色
     * */
    void create(Session session, String accountId, String name, int job, int sex);

    /**
     * 获取角色信息
     * */
    void getRoleMessage(String accountId, Long rId, final Session session);

    /** 角色经验增加 */
    int addExperience(Long rId, int experienceNum);


    /**
     * 更新人物属性
     */
    void updateAttributeModule(String name, Map<AttributeType, AttributeElement> map, Long rId);

    /** 返回角色属性 */
    Map<AttributeType, AttributeElement> getAttributes(Long rId);
}
