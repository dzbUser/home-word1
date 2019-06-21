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
    public int propUser(String accountId, Long rId) {
        return PromptCode.UNAVAILABLE;
    }

    public EmptyProps() {
        setNum(0);
        setId(0);
    }
}
