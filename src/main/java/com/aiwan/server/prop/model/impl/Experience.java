package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.prop.model.PropsType;
import com.aiwan.server.user.backpack.model.Backpack;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;


/**
 * @author dengzebiao
 * @since 2019.6.19
 * 经验丹
 */
public class Experience extends AbstractProps {
    @Override
    public int propUse(String accountId, Long rId, int num) {
        //获取背包
        Backpack backpack = GetBean.getBackPackManager().load(accountId);
        //扣除道具
        backpack.deductionPropByObjectId(getObjectId(), num);
        //增加经验
        int experienceNum = getPropsResource().getEffect() * num;
        //角色经验添加
        int surplusExperience = GetBean.getRoleService().addExperience(rId, experienceNum);
        if (surplusExperience > 0) {
            //获取剩余数量
            int surplusNum = surplusExperience / getPropsResource().getEffect();
            //创建剩余道具返回背包
            AbstractProps abstractProps = PropsType.EXPERIENCE.createProp();
            abstractProps.init(this.getPropsResource());
            abstractProps.setNum(surplusNum);
            //存回背包中
            backpack.putOverlayProps(abstractProps);
            //人物达到最高级
            GetBean.getBackPackManager().writeBack(backpack);
            return PromptCode.ROLEACHIEVEMAXLEVEL;
        }
        //写回
        GetBean.getBackPackManager().writeBack(backpack);
        return PromptCode.USERSUCCESS;
    }
}
