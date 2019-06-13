package com.aiwan.server.prop.service;

import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.backpack.protocol.CM_ObtainProp;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 道具业务类
 * */
public interface PropService {

    /** 获取道具
     * @param id 道具id
     * */
    Props getProp(int id);

    /** 生成道具到角色背包 */
    void obtainProp(CM_ObtainProp cm_obtainProp, Session session);

}
