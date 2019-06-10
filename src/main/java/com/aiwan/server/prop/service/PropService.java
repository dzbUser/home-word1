package com.aiwan.server.prop.service;

import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.publicsystem.common.Session;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 道具业务类
 * */
public interface PropService {

    /** 生成道具 */
    Props getProp(int id);

    /**
     * @return 0:使用失败 1：使用成功
     * 使用经验丹 */
    int userExperience(String accountId,Long rId,int pId, Session session);

    /**
     * @return 0:使用失败 1：使用成功
     * 使用坐骑升阶丹 */
    int userMountDan(String accountId,Long rId, int pId, Session session);

    /**
     * @return 0:装备失败 1：装备成功
     * 装备 */
    int equip(String accountId,Long rId, int pId, Session session);
}
