package com.aiwan.server.role.player.service;

import com.aiwan.server.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.server.role.player.entity.RoleEnt;
import com.aiwan.server.role.player.model.Role;
import com.aiwan.server.util.IDUtil;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @author dengzebiao
 * 角色缓存管理
 * */
@Service
public class RoleManager {
    private EntityCaheServiceImpl<Long, RoleEnt> cache;


    /**
     * 创建角色
     * */
    public Long createRole(String accountId,int sex,int job){
        RoleEnt roleEnt = new RoleEnt();
        Long id = IDUtil.getId();
        roleEnt.setId(id);
        roleEnt.setAccountId(accountId);
        roleEnt.setLevel(0);
        roleEnt.setJob(job);
        roleEnt.setSex(sex);
        Long time = Calendar.getInstance().getTimeInMillis();
        roleEnt.setUpdateTime(time);
        roleEnt.setCreatTime(time);
        cache.writeBack(id,roleEnt);
        return id;
    }

    /**
     * 获取角色
     * */
    public Role load(Long id){
        RoleEnt roleEnt = cache.load(id);
        Role role = new Role();
        role.setRoleEnt(roleEnt);
        return role;
    }

    /**
     *保存角色
     * */
    public void sava(Role role){
        RoleEnt roleEnt = role.getRoleEnt();
        roleEnt.setUpdateTime(Calendar.getInstance().getTimeInMillis());
        cache.writeBack(roleEnt.getId(),roleEnt);
    }
}