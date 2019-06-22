package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.util.PromptCode;


/**
 * 空道具
 *
 * @author dengzebiao
 * @since 2019.6.19
 */
public class EmptyProps extends AbstractProps {
    @Override
    public int propUse(String accountId, Long rId, int num, int position) {
        return PromptCode.UNAVAILABLE;
    }

    public EmptyProps() {
        setNum(0);
        setResourceId(0);
    }
}
