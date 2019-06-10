package com.aiwan.server.prop.service.impl;

import com.aiwan.server.prop.annotation.PropUse;
import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.prop.service.PropService;
import com.aiwan.server.prop.service.PropsManager;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 道具业务类
 * */
@Service
public class PropServiceImpl implements PropService {

    @Autowired
    private PropsManager propsManager;

    /** 生成道具 */
    @Override
    public Props getProp(int id) {
        return propsManager.getProps(id);
    }

    /** 使用经验丹 */
    @PropUse(type = 1)
    @Override
    public int userExperience(String accountId,Long rId,int pId, Session session) {
        //增加1000经验
        int experienceNum = 1000;
        //角色经验添加
        int num = GetBean.getRoleService().addExperience(accountId,rId,experienceNum);
        if (num == 0){
            //人物达到最高级
            Props props = GetBean.getPropsManager().getProps(pId);
            session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,"您的角色已达到最高级"));
            return 0;
        }
        Props props = GetBean.getPropsManager().getProps(pId);
        session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,props.getName()+"使用成功！"));
        return 1;
    }

    @PropUse(type = 2)
    @Override
    public int userMountDan(String accountId,Long rId, int pId, Session session) {
        int status = GetBean.getMountService().addExperience(rId,1000);
        if (status == 0){
            //达到最高等级
            Props props = GetBean.getPropsManager().getProps(pId);
            session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,"您的坐骑已达到最高级"));
            return 0;
        }
        Props props = GetBean.getPropsManager().getProps(pId);
        session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,props.getName()+"使用成功！"));
        return 1;
    }

    @PropUse(type = 3)
    @Override
    public int equip(String accountId,Long rId, int pId, Session session) {
        //装备使用
        int id = GetBean.getRoleService().equip(accountId,rId,pId);
        //装备错误
        if (id == -1){
            session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,"您的等级未达到要求"));
            return 0;
        }
        if (id != 0){
            //获取旧的装备
            Props oldEquipment  = GetBean.getPropsManager().getProps(id);
            //旧的装备存到背包
            GetBean.getBackpackService().obtainProp(accountId,oldEquipment,1);
        }
        Props props = GetBean.getPropsManager().getProps(pId);
        session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,props.getName()+"使用成功！"));
        return 1;
    }


}
