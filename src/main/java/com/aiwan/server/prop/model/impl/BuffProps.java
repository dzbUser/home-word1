package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.user.backpack.model.Backpack;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;

/**
 * buff道具
 *
 * @author dengzebiao
 */
public class BuffProps extends AbstractProps {

    @Override
    public int propUse(String accountId, Long rId, int num) {
        if (num > 1) {
            return PromptCode.USER_ONLY_ONE;
        }
        //扣除道具
        Backpack backpack = GetBean.getBackPackManager().load(accountId);
        //扣除道具
        backpack.deductionPropByObjectId(getObjectId(), num);
        //写回
        GetBean.getBackPackManager().writeBack(backpack);
        //添加buff
        GetBean.getBuffService().addBuff(rId, getPropsResource().getBuffId(), getPropsResource().getDelay());
        return PromptCode.USERSUCCESS;
    }


}
