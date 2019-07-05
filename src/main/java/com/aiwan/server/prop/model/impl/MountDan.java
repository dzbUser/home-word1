package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;


/**
 * @author dengzebiao
 * @since 2019.6.19
 * 坐骑升阶丹
 */
public class MountDan extends AbstractProps {


    @Override
    public int propUse(String accountId, Long rId, int num) {
        return PromptCode.UNAVAILABLE;
    }
}
