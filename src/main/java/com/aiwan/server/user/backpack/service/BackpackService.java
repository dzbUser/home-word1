package com.aiwan.server.user.backpack.service;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.prop.protocol.CM_PropUse;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.backpack.protocol.CM_ObtainProp;
import com.aiwan.server.user.backpack.protocol.CM_ViewBackpack;

/**
 * @author dengzebiao
 * @since 2019.6.4
 * 背包业务
 * */
public interface BackpackService {
    /** 创建背包
     * @param acountId 用户账号
     * */
    void createBackpack(String acountId);

    /** 添加可叠加道具
     * @param accountId 用户账号
     * @param abstractProps 道具
     * @param num 道具数量
     * @return false 背包已满，true添加成功
     * */
    boolean obtainOverlayProp(String accountId, AbstractProps abstractProps, int num);

    /** 查看背包
     * @param accountId 用户账号
     * @param session 会话
     * */
    void viewBackpack(String accountId, Session session);

    /** 道具使用 */
    void propUse(String accountId, int position, int num, Long rId, Session session);

//    /**扣除背包中的道具
//     * @param accountId 用户账号
//     * @param abstractProps 道具id
//     * @return 1：扣除成功 2：扣除失败
//     * */
//    int deductionProp(String accountId, AbstractProps abstractProps);

    /** 添加道具到背包 */
    void addPropToBack(String accountId, int resourceId, int num, Session session);

    /**
     * 添加不可叠加
     * @return false 背包已满，true添加成功
     */
    boolean obtainNoOverlayProp(String accountId, AbstractProps abstractProps);

    /**
     * 丢弃道具
     *
     * @param position 背包位置
     * @param num      丢弃数量
     */
    void dropProps(String accountId, int position, int num, Session session);

    /**
     * 使用装备
     */
    void userEquipment(String accountId, int position, Long rid, Session session);

}
