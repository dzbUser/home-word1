package com.aiwan.server.user.account.service;

import com.aiwan.server.prop.protocol.CM_PropUse;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.attributes.protocol.CM_ViewAttributes;
import com.aiwan.server.user.role.equipment.CM_ViewEquipBar;
import com.aiwan.server.user.role.mount.protocol.CM_ViewMount;
import com.aiwan.server.user.role.player.protocol.CM_RoleMessage;
import com.aiwan.server.scenes.protocol.CM_Move;
import com.aiwan.server.scenes.protocol.CM_Shift;
import com.aiwan.server.user.account.protocol.*;
import com.aiwan.server.user.backpack.protocol.CM_ObtainProp;
import com.aiwan.server.user.backpack.protocol.CM_ViewBackpack;

/**
 * @author dengzebiao
 * 用户业务逻辑类
 * */
public interface UserService {
//    public User getUserByUid(int uid);

    /**
     * 用户登录
     * */
    public void login(CM_Login userMessage, Session session);

    /**
     * 用户注册
     * */
    public void registUser(CM_Registered userMessage, Session session);

    /**
     * 用户注销
     * */
    public void logout(CM_Logout userMessage, Session session);

    /**
     * 高级登录
     * */
    public void hLogin(CM_HLogin cm_hlogin,Session session);

    /**
     * 用户移动
     * */
    public void move(CM_Move cm_move,Session session);

    /**
     * 地图跳转
     * */
    public void shift(CM_Shift cm_shift, Session session);

    /**
     * 创建角色
     */
    public void createRole(CM_CreateRole cm_createRole,Session session);

    /**
     * 获取角色信息
     * */
    public void getRoleMessage(CM_RoleMessage cm_roleMessage,Session session);

    /** 添加道具 */
    void  obtainProp(CM_ObtainProp cm_obtainProp,Session session);

    /** 查看背包 */
    void viewBackpack(CM_ViewBackpack cm_viewBackpack,Session session);

    /** 使用道具 */
    void propUse(CM_PropUse cm_propUser, Session session);

    /** 查看角色装备栏 */
    void viewEquip(CM_ViewEquipBar cm_viewEquipBar,Session session);

    /** 查看坐骑信息 */
    void viewMount(CM_ViewMount cm_viewMount,Session session);

    /** 删除人物储存 */
    void deleteSave(String accountId);


}
