package com.aiwan.server.user.account.service;

import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.server.user.account.entity.UserEnt;
import com.aiwan.server.user.account.model.User;
import com.aiwan.server.user.account.model.UserBaseInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author dengzebiao
 * 用户缓存操作类
 * */
@Manager
public class UserManager {
    private EntityCaheServiceImpl<String, UserEnt> cache;

    /**
     * 获取用户
     * @param id
     * */
    public User getUserByAccountId(String id){
        UserEnt userEnt = cache.load(id);
        if (userEnt == null){
            return null;
        }
        User user = new User(userEnt);
        return user;
    }

    /**
     * 保存用户
     * */
    public void save(User user){
        UserEnt userEnt = user.getUserEnt();
        cache.writeBack(user.getAcountId(),userEnt);
    }

    /**
     * 创建角色
     * */
    public void register(String acountId,String password,String hpassword,int map,int x,int y,int maxRole){
        //初始化信息
        UserEnt userEnt = new UserEnt();
        userEnt.setAcountId(acountId);
        userEnt.setPassword(password);
        userEnt.setHpassword(hpassword);
        userEnt.setMap(map);
        userEnt.setCurrentX(x);
        userEnt.setCurrentY(y);
        userEnt.setMaxRole(maxRole);
        //初始化用户信息
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setRoles(new ArrayList<Long>());
        userEnt.setUserBaseInfo(userBaseInfo);
        cache.writeBack(acountId,userEnt);
    }

}
