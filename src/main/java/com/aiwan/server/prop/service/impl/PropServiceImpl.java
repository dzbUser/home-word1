package com.aiwan.server.prop.service.impl;

import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.prop.service.PropService;
import com.aiwan.server.prop.service.PropsManager;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.user.backpack.protocol.CM_ObtainProp;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 道具业务类
 * */
@Service
public class PropServiceImpl implements PropService {

    Logger logger = LoggerFactory.getLogger(PropService.class);

    @Autowired
    private PropsManager propsManager;

    /** 生成道具 */
    @Override
    public Props getProp(int id) {
        return propsManager.getProps(id);
    }

    @Override
    public void obtainProp(CM_ObtainProp cm_obtainProp, Session session) {
        /*
         * 1.获取道具
         * 2.若道具为空，则发送获取错误
         * */
        Props props = GetBean.getPropService().getProp(cm_obtainProp.getId());
        if (props == null){
            DecodeData decodeData = SMToDecodeData.shift(StatusCode.MESSAGE,"没有该道具");
            session.messageSend(decodeData);
            return;
        }
        //添加该道具到背包
        GetBean.getBackpackService().obtainProp(cm_obtainProp.getAccountId(),props,cm_obtainProp.getNum());
        logger.info("用户："+cm_obtainProp.getAccountId()+"添加"+props.getName()+"成功");
        DecodeData decodeData = SMToDecodeData.shift(StatusCode.MESSAGE,props.getName()+"添加成功");
        session.messageSend(decodeData);
    }


}
