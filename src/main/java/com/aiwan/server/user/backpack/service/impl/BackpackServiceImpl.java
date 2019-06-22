package com.aiwan.server.user.backpack.service.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.prop.model.PropsType;
import com.aiwan.server.prop.protocol.CM_PropUse;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.publicsystem.common.Session;
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

    /**
     * 添加可叠加道具
     */
    @Override
    public boolean obtainOverlayProp(String accountId, AbstractProps abstractProps, int num) {
        Backpack backpack = backPackManager.load(accountId);
        //可叠加
        boolean status = backpack.putOverlayProps(abstractProps, num);
        backPackManager.writeBack(backpack);
        //如果添加道具成功
        return status;
    }

    /**
     * 添加不可叠加道具
     */
    @Override
    public boolean obtainNoOverlayProp(String accountId, AbstractProps abstractProps) {
        Backpack backpack = backPackManager.load(accountId);
        //不可叠加
        boolean status = backpack.putNoOverlayProps(abstractProps);
        backPackManager.writeBack(backpack);
        return status;
    }

    /**
     * 丢弃道具
     */
    @Override
    public void dropProps(String accountId, int position, int num, Session session) {
        logger.info(accountId + "丢弃道具" + " 位置：" + position);
        Backpack backpack = backPackManager.load(accountId);
        AbstractProps abstractProps = backpack.getPropByPosition(position);
        if (!backpack.deductionPropInPosition(position, num)) {
            logger.info(accountId + ":丢弃失败" + " 位置：" + position);
            //该位置没有道具,或者不够数量
            session.sendPromptMessage(PromptCode.DRAOPFAIL, "");
        } else {
            logger.info(accountId + ":丢弃成功" + " 位置：" + position);
            //丢弃成功
            backPackManager.writeBack(backpack);
            session.sendPromptMessage(PromptCode.DROPSUCCESS, abstractProps.getPropsResource().getName());
        }
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
        logger.info("查看用户：{}的背包", cm_viewBackpack.getAccountId());
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
            PropInfo propInfo = PropInfo.valueOf(abstractProps.getResourceId(), abstractProps.getNum());
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
        AbstractProps abstractProps = backpack.getPropByPosition(cm_propUser.getPosition());
        if (abstractProps == null || abstractProps.getResourceId() == PropsType.emptyId) {
            //该位置没有道具
            session.sendPromptMessage(PromptCode.NOPOSITIONINBACK, "");
            logger.info(cm_propUser.getAccountId() + "使用位置" + cm_propUser.getPosition() + ":没有该位置的道具");
            return;
        } else if (abstractProps.getNum() < cm_propUser.getNum()) {
            session.sendPromptMessage(PromptCode.PROPNUMINSUFFICIENT, "");
            logger.info("用户{}使用{}数量为{}的道具数量不足", cm_propUser.getAccountId(), cm_propUser.getrId(), cm_propUser.getNum());
            return;
        }
        if (abstractProps.getPropsResource().getUse() == 0) {
            //该道具不可使用
            session.sendPromptMessage(PromptCode.UNAVAILABLE, abstractProps.getPropsResource().getName());
            logger.info(cm_propUser.getAccountId() + "使用位置" + cm_propUser.getPosition() + ":该道具不可使用");
            return;
        }
        int code = abstractProps.propUse(cm_propUser.getAccountId(), cm_propUser.getrId(), cm_propUser.getNum(), cm_propUser.getPosition());
        session.sendPromptMessage(code, "");
    }


    /** 添加道具到背包 */
    @Override
    public void addPropToBack(CM_ObtainProp cm_obtainProp, Session session) {
        PropsResource propsResource = GetBean.getPropsManager().getPropsResource(cm_obtainProp.getId());
        if (propsResource == null) {
            //没有该类道具
            session.sendPromptMessage(PromptCode.NOPROP, "");
            logger.info(cm_obtainProp+":添加道具失败，没有该道具");
            return;
        }
        //查看是否可以存该数量的道具
        Backpack backpack = backPackManager.load(cm_obtainProp.getAccountId());
        if (!backpack.isCanSavePropsInNum(cm_obtainProp.getId(), cm_obtainProp.getNum())) {
            //不可存该数量的道具
            logger.info("用户：{}的背包不可存数量：{}的，id为：{}的道具", cm_obtainProp.getAccountId(), cm_obtainProp.getNum(), cm_obtainProp.getId());
            session.sendPromptMessage(PromptCode.BACKFULL, "");
            return;
        }
        if (propsResource.getOverlay() == 1) {
            //可叠加道具添加
            AbstractProps abstractProps = PropsType.getType(propsResource.getType()).createProp();
            abstractProps.init(propsResource);
            backpack.putOverlayProps(abstractProps, cm_obtainProp.getNum());
            backPackManager.writeBack(backpack);
            session.sendPromptMessage(PromptCode.ADDSUCCESS, propsResource.getName());
            return;
        }
        //不可叠加道具添加
        int num = cm_obtainProp.getNum();
        for (int i = 0; i < cm_obtainProp.getNum(); i++) {
            AbstractProps abstractProps = PropsType.getType(propsResource.getType()).createProp();
            abstractProps.init(propsResource);
            obtainNoOverlayProp(cm_obtainProp.getAccountId(), abstractProps);

        }

        logger.info("用户："+cm_obtainProp.getAccountId()+"添加"+ propsResource.getName()+"成功");
        session.sendPromptMessage(PromptCode.ADDSUCCESS, propsResource.getName());
    }

}
