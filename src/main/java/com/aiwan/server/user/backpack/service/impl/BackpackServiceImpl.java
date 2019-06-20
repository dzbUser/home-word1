package com.aiwan.server.user.backpack.service.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.prop.model.PropsType;
import com.aiwan.server.prop.protocol.CM_PropUse;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.publicsystem.protocol.SM_PromptMessage;
import com.aiwan.server.user.backpack.protocol.CM_ObtainProp;
import com.aiwan.server.user.protocol.Item.PropInfo;
import com.aiwan.server.user.backpack.model.Backpack;
import com.aiwan.server.user.backpack.protocol.CM_ViewBackpack;
import com.aiwan.server.user.protocol.SM_PropList;
import com.aiwan.server.user.backpack.service.BackPackManager;
import com.aiwan.server.user.backpack.service.BackpackService;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.4
 * 背包业务
 * */
@Service
public class BackpackServiceImpl implements BackpackService {
    private Logger logger = LoggerFactory.getLogger(BackpackServiceImpl.class);

    /** 注入管理类 */
    @Autowired
    private BackPackManager backPackManager;

    /** 创建背包 */
    @Override
    public void createBackpack(String acountId) {
        backPackManager.createBackpack(acountId);
    }

    /** 获取道具 */
    @Override
    public boolean obtainProp(String accountId, int pid) {
        Backpack backpack = backPackManager.load(accountId);
        //如果添加道具成功
        if (backpack.putProps(pid)) {
            //写回
            backPackManager.writeBack(backpack);
            return true;
        }
        return false;
    }

    /** 查看背包 */
    @Override
    public void viewBackpack(CM_ViewBackpack cm_viewBackpack, Session session) {

        /*
        * 1.获取背包数据
        * 2.若背包为空，直接返回背包为空
        * 3.遍历所有背包项
        * 4.若可以叠加直接输出
        * 5.若不可以，则一个一个输出道具名
        * */
        logger.info("查看用户："+cm_viewBackpack.getAccountId()+"的背包");
        Backpack backpack = backPackManager.load(cm_viewBackpack.getAccountId());
        //获取背包
        if (backpack.isEmpty()) {
            //背包为空
            session.sendPromptMessage(PromptCode.BACKEMPTY, "");
            return;
        }
        //创建背包返回类
        SM_PropList sm_propList = new SM_PropList();
        //获取背包数组
        AbstractProps[] array = backpack.getBackpackInfo().getAbstractProps();
        List<PropInfo> list = new ArrayList<PropInfo>();
        //添加背包道具
        for (AbstractProps abstractProps : array) {
            PropInfo propInfo = PropInfo.valueOf(abstractProps.getId(), abstractProps.getNum());
            list.add(propInfo);
        }
        sm_propList.setList(list);
        session.messageSend(SMToDecodeData.shift(StatusCode.VIEWPROPLIST, sm_propList));
    }

    /** 道具使用 */
    @Override
    public void propUse(CM_PropUse cm_propUser, Session session) {
        logger.info(cm_propUser.getAccountId() + "使用" + cm_propUser.getPosition() + "位置的道具");
        //获取背包
        Backpack backpack = backPackManager.load(cm_propUser.getAccountId());
        if (cm_propUser.getPosition() > backpack.getMaxNum()) {
            //没有该位置的道具，返回错误提示
            session.sendPromptMessage(PromptCode.NOPOSITIONINBACK, "");
            logger.info(cm_propUser.getAccountId() + "使用位置" + cm_propUser.getPosition() + ":没有该位置的道具");
            return;
        }

        //有该位置的道具
        AbstractProps props = backpack.getPropByPosition(cm_propUser.getPosition());
        if (props.getId() == PropsType.emptyId) {
            //该背包位置为空
            session.sendPromptMessage(PromptCode.EMPTYINBACK, "");
            logger.info(cm_propUser.getAccountId() + "使用位置" + cm_propUser.getPosition() + ":该背包位置为空");
            return;
        }

        if (props.getPropsResource().getUse() == 0) {
            //该道具不可使用
            session.sendPromptMessage(PromptCode.UNAVAILABLE, props.getPropsResource().getName());
            logger.info(cm_propUser.getAccountId() + "使用位置" + cm_propUser.getPosition() + ":该道具不可使用");
            return;
        }
        int code = props.propUser(cm_propUser.getAccountId(), cm_propUser.getrId());
        session.sendPromptMessage(code, "");
    }

    /** 扣除背包中的道具 */
    @Override
    public int deductionProp(String accountId, int pId) {
        /*
        * 1.背包中是否有该道具,若无返回0
        * 2.背包道具减1，若减完为0，则从背包中消除该道具
        * */
        logger.info(accountId+"扣除道具"+pId);
        PropsResource propsResource = GetBean.getPropsManager().getPropsResource(pId);
        //获取背包
        Backpack backpack = backPackManager.load(accountId);
        //获取背包项
        AbstractProps abstractProps = backpack.getBackpackItem(pId);
        if (abstractProps == null) {
            //背包中没有该道具
            logger.info(accountId + "扣除" + propsResource.getName() + "失败！");
            return 0;
        }
        int num = abstractProps.getNum();
        num--;
        if (num == 0){
             //道具用完
            backpack.removeProps(pId);
        }else {
            //道具数
            abstractProps.setNum(num);
        }
    //写回
        backPackManager.writeBack(backpack);
        return 1;
    }


    /** 添加道具到背包 */
    @Override
    public void addPropToBack(CM_ObtainProp cm_obtainProp, Session session) {
        /*
         * 1.获取道具
         * 2.若道具为空，则发送获取错误
         * */
        PropsResource propsResource = GetBean.getPropsManager().getPropsResource(cm_obtainProp.getId());
        if (propsResource == null){
            session.sendPromptMessage(PromptCode.NOPROP, "");
            logger.info(cm_obtainProp+":添加道具失败，没有该道具");
            return;
        }
        //添加该道具到背包
        int num = cm_obtainProp.getNum();
        for (int i = 0; i < cm_obtainProp.getNum(); i++) {
            if (!obtainProp(cm_obtainProp.getAccountId(), cm_obtainProp.getId())) {
                logger.info(cm_obtainProp.getAccountId() + "背包已满");
                session.sendPromptMessage(PromptCode.BACKFULL, "");
                return;
            }
        }

        logger.info("用户："+cm_obtainProp.getAccountId()+"添加"+ propsResource.getName()+"成功");
        session.sendPromptMessage(PromptCode.ADDSUCCESS, propsResource.getName());
    }


}
