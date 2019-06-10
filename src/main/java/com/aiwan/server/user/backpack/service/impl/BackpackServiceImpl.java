package com.aiwan.server.user.backpack.service.impl;

import com.aiwan.server.prop.resource.Equipment;
import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.prop.service.PropsManager;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.PropUserManager;
import com.aiwan.server.user.backpack.model.Backpack;
import com.aiwan.server.user.backpack.model.BackpackItem;
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
    public String viewBackpack(String accountId) {

        /*
        * 1.获取背包数据
        * 2.若背包为空，直接返回背包为空
        * 3.遍历所有背包项
        * 4.若可以叠加直接输出
        * 5.若不可以，则一个一个输出道具名
        * */
        logger.info("查看用户："+accountId+"的背包");
        Backpack backpack = backPackManager.load(accountId);
        StringBuffer message = new StringBuffer();
        //获取背包
        Map<Integer,BackpackItem> map = backpack.getBackpackEnt().getBackpackInfo().getBackpackItems();
        BackpackItem backpackItem;
        if (map.size() == 0){
            //背包为空
            message.append("您的背包是空的！\n");
        }else {
            for (Map.Entry<Integer,BackpackItem> entry:map.entrySet()){
                backpackItem = entry.getValue();
                Props props = GetBean.getPropService().getProp(backpackItem.getId());
                if (props.getOverlay() == 1){
                    message.append("id:"+props.getId()+" 道具名:"+props.getName()+" 描述:"+props.getIntroduce()+" 数量:"+backpackItem.getNum()+"\n");
                }else {
                    for (int i = 0;i<backpackItem.getNum();i++){
                        message.append("id:"+props.getId()+" 道具名:"+props.getName()+" 描述:"+props.getIntroduce());
                        if (props.getType() == PropsManager.EQUIP){
                            //是装备，加装备描述
                            Equipment equipment = GetBean.getPropsManager().getEquipment(backpackItem.getId());
                            message.append(" 攻击力"+equipment.getPower()+" 血量:"+equipment.getBlood()+" 攻击加成:"+equipment.getPowerBonus()+"%"+
                                    " 等级要求："+equipment.getLevel()+"\n");
                        }else {
                            message.append("\n");
                        }
                    }
                }
            }
        }
        return message.toString();
    }

    /** 道具使用 */
    @Override
    public void propUse(String accountId, Long rId, int pId, Session session) {
        /*
        * 1.查看是否有该道具
        * 2.若有该道具，道具数-1，若道具数0,移除该道具
        * 3.调用道具使用
        * 4.写回
        * */
        Props props = GetBean.getPropsManager().getProps(pId);
        //获取背包
        Backpack backpack = backPackManager.load(accountId);
        //获取背包项
        BackpackItem backpackItem = backpack.getBackpackItem(pId);
        if (backpackItem == null ){
            //道具数不对
            logger.error(accountId+"使用"+props.getName()+"失败！");
            session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE,"您没有该道具"));
        }else {
            int num = backpackItem.getNum();
            //道具数正确
            int type = props.getType();
            //道具使用,反射使用
            int status = (int) ReflectionUtils.invokeMethod(PropUserManager.getMethod(type),GetBean.getPropService(),accountId,rId,pId,session);
            logger.debug(accountId+"使用道具"+pId+"的状态："+status);
            if (status == 1){
                //使用成功
                num--;
            }
            if (num == 0){
                //道具用完
                backpack.removeBackpackItem(pId);
            }else {
                //道具数
                backpackItem.setNum(num);
            }

            //写回
            backPackManager.writeBack(backpack);
        }

    }


}
