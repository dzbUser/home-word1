package com.aiwan.server.prop.model.impl;

import com.aiwan.server.prop.annotation.PropType;
import com.aiwan.server.prop.model.PropUseInterface;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.util.StatusCode;

/**
 * @author dengzebiao
 * @since 2019.6.11
 * 使用经验
 * */
@PropType(type = 1)
public class ExperienceDan implements PropUseInterface {

    /** 使用道具 */
    @Override
    public void propUse(String accountId, Long rId, int pId, Session session) {
        //获取道具类
        PropsResource propsResource = GetBean.getPropsManager().getProps(pId);
        //扣除道具
        int status = GetBean.getBackpackService().deductionProp(accountId,pId);
        if (status == 0){
            session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,"您背包中没有"+ propsResource.getName()));
            return ;
        }
        //增加1000经验
        int experienceNum = 1000;
        //角色经验添加
        int num = GetBean.getRoleService().addExperience(accountId,rId,experienceNum);
        if (num == 0){
            //人物达到最高级
            session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,"您的角色已达到最高级"));
            return ;
        }
        session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE, propsResource.getName()+"使用成功！"));
        return ;
    }
}
