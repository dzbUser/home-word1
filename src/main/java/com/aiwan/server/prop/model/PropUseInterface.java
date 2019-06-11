package com.aiwan.server.prop.model;

import com.aiwan.server.publicsystem.common.Session;

/**
 * @author dengzebiao
 * @since 2019.6.11
 * 道具接口类
 * */
public interface PropUseInterface {
    /**
     * 使用道具
     * @param accountId 账号id
     * @param rId 角色id
     * @param pId 道具id
     * @param session 会话
     * @return 0:使用失败 1：使用成功
     * */
    public int propUse(String accountId,Long rId, int pId, Session session);
}
