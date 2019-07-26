package com.aiwan.server.user.role.player.service;

import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.ramcache.orm.Accessor;
import com.aiwan.server.ramcache.service.impl.EntityCacheServiceImpl;
import com.aiwan.server.user.role.player.entity.RoleEnt;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.powerboard.model.RankInfo;
import com.aiwan.server.util.IDUtil;
import org.apache.poi.ss.formula.functions.Rank;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author dengzebiao
 * 角色缓存管理
 * */
@Manager
public class RoleManager {


    private EntityCacheServiceImpl<Long, RoleEnt> cache;

    @Autowired
    private Accessor accessor;


    /**
     * 创建角色
     * */
    public Role createRole(String accountId, String name, int sex, int job, int map, int x, int y) {
        RoleEnt roleEnt = new RoleEnt();
        Long id = IDUtil.getId();
        roleEnt.setId(id);
        roleEnt.setAccountId(accountId);
        roleEnt.setLevel(1);
        roleEnt.setJob(job);
        roleEnt.setSex(sex);
        roleEnt.setName(name);
        roleEnt.setMap(map);
        roleEnt.setX(x);
        roleEnt.setY(y);
        roleEnt.setSceneId(0);
        Long time = Calendar.getInstance().getTimeInMillis();
        roleEnt.setUpdateTime(time);
        roleEnt.setCreatTime(time);
        Role role = new Role();
        role.setRoleEnt(roleEnt);
        role.createModule();
        cache.writeBack(id,roleEnt);
        return role;
    }

    /**
     * 获取角色
     * */
    public Role load(Long id){
        RoleEnt roleEnt = cache.load(id);
        if (roleEnt == null) {
            return null;
        }
        Role role = roleEnt.getRole();
        if (role == null) {
            role = Role.valueOf(roleEnt);
            roleEnt.setRole(role);
        }
        return role;
    }

    /**
     *保存角色
     * */
    public void save(Role role) {
        RoleEnt roleEnt = role.getRoleEnt();
        roleEnt.setUpdateTime(Calendar.getInstance().getTimeInMillis());
        cache.writeBack(roleEnt.getId(),roleEnt);
    }

    /**
     * 获取战力排行的角色
     *
     * @param num 数目
     * @return
     */
    public List<RoleEnt> getRoleSortByCombat(int num) {
        String hql = "from RoleEnt order by combatPower desc,updateTime";
        List<RoleEnt> list = accessor.find(hql, 0, num);
        CopyOnWriteArrayList<RankInfo> rankInfos = new CopyOnWriteArrayList<>();
        return list;
    }

}
