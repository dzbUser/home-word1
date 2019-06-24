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
        //获取最大容量
        int maxNum = GetBean.getBackResourceManager().getBackResource().getMaxNum();
        BackpackEnt backpackEnt = new BackpackEnt();
        backpackEnt.setAccountId(accountId);
        backpackEnt.setMaxNum(maxNum);
        Long time = Calendar.getInstance().getTimeInMillis();
        backpackEnt.setUpdateTime(time);
        backpackEnt.setCreateTime(time);
        backpackEnt.setBackpackInfo(new BackpackInfo(maxNum));
        cache.writeBack(backpackEnt.getAccountId(),backpackEnt);
    }
}
