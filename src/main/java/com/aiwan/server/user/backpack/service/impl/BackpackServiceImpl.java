package com.aiwan.server.user.backpack.service.impl;

import com.aiwan.server.prop.protocol.CM_PropUse;
import com.aiwan.server.prop.resource.Equipment;
import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.prop.service.PropsManager;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.PropUserManager;
import com.aiwan.server.user.Item.PropInfo;
import com.aiwan.server.user.backpack.model.Backpack;
import com.aiwan.server.user.backpack.model.BackpackItem;
import com.aiwan.server.user.backpack.protocol.CM_ViewBackpack;
import com.aiwan.server.user.backpack.protocol.SM_Package;
import com.aiwan.server.user.backpack.service.BackPackManager;
import com.aiwan.server.user.backpack.service.BackpackService;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.4
 * 背包业务
 * */
@Service
public class BackpackServiceImpl implements BackpackService {
    Logger logger = LoggerFactory.getLogger(BackpackServiceImpl.class);

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
    public void obtainProp(String accountId, Props props, int num) {
        /*
         * 1.判断是否可以叠加
         * 2.查看背包中是否有该道具
         * 3.若无，则创建道具项
         * 4.若有，则添加到其中
         * */
        Backpack backpack = backPackManager.load(accountId);
        //获取背包中该道具
        BackpackItem backpackItem = backpack.getBackpackItem(props.getId());
        //若无该道具
        if (backpackItem == null){
            //背包无该道具
            backpackItem = new BackpackItem();
            backpackItem.setId(props.getId());
            //无期限
            backpackItem.setLimit(false);
            backpackItem.setDeadline(0L);
            backpackItem.setNum(num);
            backpack.putBackpackItem(backpackItem);
        }else {
            //背包有该道具
            backpackItem.setNum(backpackItem.getNum()+num);
        }
        //写回
        backPackManager.writeBack(backpack);
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
        StringBuffer message = new StringBuffer();
        //获取背包
        Map<Integer,BackpackItem> map = backpack.getBackpackEnt().getBackpackInfo().getBackpackItems();
        BackpackItem backpackItem;
        if (map.size() == 0) {
            //背包为空
            message.append("您的背包是空的！\n");
        }
        //创建背包返回类
        SM_Package sm_package = new SM_Package();

        //添加背包道具
        for (Map.Entry<Integer,BackpackItem> entry:map.entrySet()){
            Props props = GetBean.getPropsManager().getProps(entry.getValue().getId());
            if (props.getOverlay() == 0){
                //不可叠加
                for (int i = 0;i<entry.getValue().getNum();i++){
                    //叠加添加道具
                    PropInfo propInfo = PropInfo.valueOf(entry.getValue().getId(),1);
                    sm_package.putProp(propInfo);
                }
            }else {
                PropInfo propInfo = PropInfo.valueOf(entry.getValue().getId(),entry.getValue().getNum());
                sm_package.putProp(propInfo);
            }
        }
        session.messageSend(SMToDecodeData.shift(StatusCode.VIEWPACKAGE,sm_package));
    }

    /** 道具使用 */
    @Override
    public void propUse(CM_PropUse cm_propUser, Session session) {
        Props props = GetBean.getPropsManager().getProps(cm_propUser.getpId());
        logger.debug(cm_propUser.getAccountId()+"使用"+props.getName());
        if (props.getUse() == 0){
            //道具不可使用
            session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,props.getName()+"不可以使用"));
            return;
        }
        //对应道具的使用
        PropUserManager.getPropUse(props.getType()).propUse(cm_propUser.getAccountId(),cm_propUser.getrId(),cm_propUser.getpId(),session);
    }

    /** 扣除背包中的道具 */
    @Override
    public int deductionProp(String accountId, int pId) {
        /*
        * 1.背包中是否有该道具,若无返回0
        * 2.背包道具减1，若减完为0，则从背包中消除该道具
        * */
        Props props = GetBean.getPropsManager().getProps(pId);
        //获取背包
        Backpack backpack = backPackManager.load(accountId);
        //获取背包项
        BackpackItem backpackItem = backpack.getBackpackItem(pId);
        if (backpackItem ==null){
            //背包中没有该道具
            logger.error(accountId+"使用"+props.getName()+"失败！");
            return 0;
        }
        int num = backpackItem.getNum();
        num--;
        if (num == 0){
             //道具用完
            backpack.removeBackpackItem(pId);
        }else {
            //道具数
            backpackItem.setNum(num);
        }
    //写回
        backPackManager.writeBack(backpack);
        return 1;
    }


}
