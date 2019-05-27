package com.aiwan.role.service;

import com.aiwan.common.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.role.entity.RoleEnt;
import com.aiwan.role.model.Role;
import com.aiwan.util.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
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
        roleEnt.setUpdateTime(System.currentTimeMillis());
        roleEnt.setCreatTime(System.currentTimeMillis());
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
        cache.writeBack(roleEnt.getId(),roleEnt);
    }
}
