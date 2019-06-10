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

    /** 获取对应职业 */
    public String getJob(int job){
        return roleResource.getJobs().get(job);
    }

    /** 获取对应性别 */
    public String getSex(int sex){
        return roleResource.getSexs().get(sex);
    }

    /** 获取对应装备名字 */
    public String getEquip(int equip){
        return roleResource.getEquipments().get(equip);
    }

    /** 获取对应坐骑名字 */
    public String getMount(int mount){
        return roleResource.getMountNames().get(mount);
    }

    /** 获取人物最高等级 */
    public int getMaxlevel(){
        return roleResource.getMaxLevel();
    }

    /** 获取坐骑最高等级 */
    public int getMaxMountLevel(){
        return roleResource.getMaxMountLevel();
    }
}
