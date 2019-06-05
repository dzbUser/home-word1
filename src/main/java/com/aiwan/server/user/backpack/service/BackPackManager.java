package com.aiwan.server.user.backpack.service;

import com.aiwan.server.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.server.user.backpack.entity.BackpackEnt;
import com.aiwan.server.user.backpack.model.Backpack;
import com.aiwan.server.user.backpack.model.BackpackInfo;
import org.springframework.stereotype.Service;

import java.util.Calendar;

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
}