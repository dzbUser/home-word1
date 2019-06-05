package com.aiwan.server.prop.service.impl;

import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.prop.service.PropService;
import com.aiwan.server.prop.service.PropsManager;
import com.aiwan.server.util.GetBean;
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
    @Override
    public void userExperience(String accountId,Long rId) {
        //增加1000经验
        int experienceNum = 1000;
        //角色经验添加
        GetBean.getRoleService().addExperience(accountId,rId,experienceNum);
    }


}
