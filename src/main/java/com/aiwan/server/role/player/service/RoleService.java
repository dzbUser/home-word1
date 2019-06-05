package com.aiwan.server.role.player.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.role.player.protocol.CM_RoleMessage;
import com.aiwan.server.user.account.protocol.CM_CreateRole;

/**
 * @author dengzebiao
 * 角色业务类
 * */
public interface RoleService {
    /**
     * 创建角色
     * */
    public void create(Session session, CM_CreateRole cm_createRole);

    /**
     * 获取角色信息
     * */
    public void getRoleMessage(Session session, CM_RoleMessage cm_roleMessage);

    /** 角色经验增加 */
    void addExperience(String accountId,Long rId,int experienceNum);

    /** 装备装备 */
    int equip(String accountId,Long rId,int pId);
}
