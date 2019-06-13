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
    void create(Session session, CM_CreateRole cm_createRole);

    /**
     * 获取角色信息
     * */
    void getRoleMessage(Session session, CM_RoleMessage cm_roleMessage);

    /** 角色经验增加 */
    int addExperience(String accountId,Long rId,int experienceNum);

    /** 装备装备 */
    int equip(String accountId,Long rId,int pId);

    /** 修改人物属性 */
    void putAttributeModule(String name,Map<AttributeType, AttributeElement> map,Long rId);

    /** 返回角色属性 */
    Map<AttributeType, AttributeElement> getAttributes(Long rId);
}