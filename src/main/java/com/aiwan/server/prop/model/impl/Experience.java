package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.util.GetBean;


/**
 * @author dengzebiao
 * @since 2019.6.19
 * 经验丹
 */
public class Experience extends AbstractProps {
    @Override
    public boolean propUser(String accountId, Long rId) {
        //扣除道具
        int status = GetBean.getBackpackService().deductionProp(accountId, getId());
        if (status == 0) {
            GetBean.getBackpackService().obtainProp(accountId, getId());
            return false;
        }
        //增加1000经验
        int experienceNum = 1000;
        //角色经验添加
        int num = GetBean.getRoleService().addExperience(accountId, rId, experienceNum);
        if (num == 0) {
            //人物达到最高级
            return false;
        }
        return true;
    }
}
