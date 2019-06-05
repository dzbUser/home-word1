package com.aiwan.server.user.backpack.service;

import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.publicsystem.common.Session;

/**
 * @author dengzebiao
 * @since 2019.6.4
 * 背包业务
 * */
public interface BackpackService {
    /** 创建背包 */
    void createBackpack(String acountId);

    /** 获取道具 */
    void obtainProp(String accountId, Props props, int num);

    /** 查看背包 */
    String viewBackpack(String accountId);

    /** 道具使用 */
    void propUse(String accountId, Long rId, int pId, Session session);
}
