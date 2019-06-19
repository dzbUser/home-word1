package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.model.AbstractProps;


/**
 * 空道具
 *
 * @author dengzebiao
 * @since 2019.6.19
 */
public class EmptyProps extends AbstractProps {
    @Override
    public boolean propUser(String accountId, Long rId) {
        return false;
    }
}
