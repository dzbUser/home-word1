package com.aiwan.server.user.role.service;

import com.aiwan.server.user.role.resource.RoleResource;
import org.springframework.stereotype.Service;

/**
 * @author dengzebiao
 * @since 2019.6.10
 * 人物资源管理类
 * */
@Service
public class RoleResourceManager {
    private RoleResource roleResource;

    public RoleResource getRoleResource() {
        return roleResource;
    }

    public RoleResourceManager setRoleResource(RoleResource roleResource) {
        this.roleResource = roleResource;
        return this;
    }



    /** 获取人物最高等级 */
    public int getMaxlevel(){
        return roleResource.getMaxLevel();
    }

}
