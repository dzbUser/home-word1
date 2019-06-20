package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.util.PromptCode;


/**
 * @author dengzebiao
 * @since 2019.6.19
 * 坐骑升阶丹
 */
public class MountDan extends AbstractProps {
    /**
     * 升阶丹id
     */
    public final static int id = 2;

    /**
     * 增加经验值
     */
    public final static int EXPERIENCE = 1000;
    @Override
    public int propUser(String accountId, Long rId) {
        return PromptCode.UNAVAILABLE;
    }
}
