package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.user.backpack.model.Backpack;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import com.sun.corba.se.impl.ior.GenericIdentifiable;


/**
 * @author dengzebiao
 * @since 2019.6.19
 * 经验丹
 */
public class Experience extends AbstractProps {
    @Override
    public int propUse(String accountId, Long rId, int num, int position) {
        //获取背包
        Backpack backpack = GetBean.getBackPackManager().load(accountId);
        //扣除道具
        if (!backpack.deductionPropInPosition(position, num)) {
            GetBean.getBackpackService().obtainOverlayProp(accountId, this, 1);
            return PromptCode.NOPROPINBACK;
        }
        //增加经验
        int experienceNum = getPropsResource().getEffect() * num;
        //角色经验添加
        int surplusExperience = GetBean.getRoleService().addExperience(accountId, rId, experienceNum);
        if (surplusExperience > 0) {
            //获取剩余数量
            int surplusNum = surplusExperience / getPropsResource().getEffect();
            backpack.putOverlayProps(this, surplusNum);
            //人物达到最高级
            GetBean.getBackPackManager().writeBack(backpack);
            return PromptCode.ROLEACHIEVEMAXLEVEL;
        }
        //写回
        GetBean.getBackPackManager().writeBack(backpack);
        return PromptCode.USERSUCCESS;
    }
}
