package com.aiwan.server.prop.service;

import com.aiwan.server.prop.resource.Props;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 道具业务类
 * */
public interface PropService {

    /** 生成道具 */
    Props getProp(int id);

    /** 使用经验丹 */
    void userExperience(String accountId,Long rId);
}
