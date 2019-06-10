package com.aiwan.server.user.role.player.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.attributes.model.AttributeModule;
import com.aiwan.server.user.role.player.protocol.CM_RoleMessage;
import com.aiwan.server.user.account.protocol.CM_CreateRole;

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
//
//    /** 获取人物属性 */
//    String getRoleAttribute(Long rId);

    /** 获取基本人物属性（新）*/
    AttributeModule getBaseAttribute(int level);

    /** 修改人物属性 */
    void putAttributeModule(String name,AttributeModule attributeModule,Long rId);
}
