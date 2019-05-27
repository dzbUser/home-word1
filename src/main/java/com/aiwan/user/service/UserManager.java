package com.aiwan.user.service;

import com.aiwan.common.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.user.entity.UserEnt;
import com.aiwan.user.model.User;
import org.springframework.stereotype.Service;


@Service("userManager")
public class UserManager {
    private EntityCaheServiceImpl<String, UserEnt> cache;

    public User getUserByAcountId(String id){
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
    public void sava(User user){
        UserEnt userEnt = user.getUserEnt();
        //序列化
        cache.writeBack(user.getAcountId(),userEnt);
    }

    /**
     * 创建角色
     * */
    public void register(String acountId,String password,String hpassword,int map,int x,int y,int maxRole){
        UserEnt userEnt = new UserEnt();
        userEnt.setAcountId(acountId);
        userEnt.setPassword(password);
        userEnt.setHpassword(hpassword);
        userEnt.setMap(map);
        userEnt.setCurrentX(x);
        userEnt.setCurrentY(y);
        userEnt.setMaxRole(maxRole);
        cache.writeBack(acountId,userEnt);
    }

}
