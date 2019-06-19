package com.aiwan.server.user.backpack.service;

import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.server.user.backpack.entity.BackpackEnt;
import com.aiwan.server.user.backpack.model.Backpack;
import com.aiwan.server.user.backpack.model.BackpackInfo;
import com.aiwan.server.user.backpack.model.BackpackItem;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.4
 * 背包业务管理类
 * */
@Service()
public class BackPackManager {


    private EntityCaheServiceImpl<String, BackpackEnt> cache;
    private final static int MAXNUM = 50;

    /**
     * 获取背包业务对象
     * */
    public Backpack load(String accountId){
        BackpackEnt backpackEnt = cache.load(accountId);
        if (backpackEnt == null){
            return null;
        }
        Backpack backpack = new Backpack();
        backpack.setBackpackEnt(backpackEnt);
        return backpack;
    }

    /**
     * 背包实体写回
     * */
    public void writeBack(Backpack backpack){
        BackpackEnt backpackEnt = backpack.getBackpackEnt();
        cache.writeBack(backpack.getAccountId(),backpackEnt);
    }

    /**
     * 背包新建
     * */
    public void createBackpack(String accountId){
        BackpackEnt backpackEnt = new BackpackEnt();
        backpackEnt.setAccountId(accountId);
        backpackEnt.setMaxNum(MAXNUM);
        Long time = Calendar.getInstance().getTimeInMillis();
        backpackEnt.setUpdateTime(time);
        backpackEnt.setCreateTime(time);
        backpackEnt.setBackpackInfo(new BackpackInfo());
        cache.writeBack(backpackEnt.getAccountId(),backpackEnt);
    }

    /** 添加道具后，背包是否为满 */
    public boolean isFull(String accountId,int num,int pid){
        //获取用户背包
        Backpack backpack = load(accountId);
        //获取背包当前道具数量
        int backNum = 0;
        Map<Integer, BackpackItem> backpackItems = backpack.getBackpackInfo().getBackpackItems();
        //遍历所有背包中的道具，获取背包所占格数
        for (Map.Entry<Integer, BackpackItem> itemEntry:backpackItems.entrySet()){
            //获取道具
            PropsResource propsResource = GetBean.getPropsManager().getProps(itemEntry.getValue().getId());
            if (propsResource.getOverlay() == 0){
                //不可叠加
                backNum += itemEntry.getValue().getNum();
            }else {
                //可叠加
                backNum++;
            }
        }
        //获取欲添加的道具
        PropsResource propsResource = GetBean.getPropsManager().getProps(pid);
        if (propsResource.getOverlay() == 1){
            //可叠加，查看背包中是否有该道具
            if (backpackItems.get(pid) == null){
                //没有改道具
                backNum++;
            }
        }else {
            //不可叠加
            backNum += num;
        }
        if (backNum > backpack.getMaxNum()){
            //大于最大容量
            return true;
        }
        return false;
    }
}
