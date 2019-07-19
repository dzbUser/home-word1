package com.aiwan.server.user.role.player.model;

import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.attributes.model.impl.RoleAttributesModule;
import com.aiwan.server.user.role.attributes.model.impl.RoleAttribute;
import com.aiwan.server.user.role.player.entity.RoleEnt;
import com.aiwan.server.util.GetBean;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author dengzebiao
 * 业务逻辑角色操作类
 * */
public class Role {
    /**
     * 实体
     */
    private RoleEnt roleEnt;

    /**
     * 是否正在地图跳转
     */
    private AtomicBoolean changingMap = new AtomicBoolean(false);

    /**
     * 是否拥有队伍
     */
    private AtomicBoolean isTeam = new AtomicBoolean(false);

    public RoleEnt getRoleEnt() {
        return roleEnt;
    }

    public Long getId() {
        return roleEnt.getId();
    }

    public void setRoleEnt(RoleEnt roleEnt) {
        this.roleEnt = roleEnt;
    }

    public Long getUpdateTime() {
        return roleEnt.getUpdateTime();
    }

    public void setUpdateTime(Long updateTime) {
        roleEnt.setUpdateTime(updateTime);
    }

    public Long getCreatTime() {
        return roleEnt.getCreatTime();
    }

    public void setCreatTime(Long creatTime) {
        roleEnt.setCreatTime(creatTime);
    }

    public int getSex() {
        return roleEnt.getSex();
    }

    public void setSex(int sex) {
        roleEnt.setSex(sex);
    }

    public int getJob() {
        return roleEnt.getJob();
    }

    public void setJob(int job) {
        roleEnt.setJob(job);
    }

    public int getLevel() {
        return roleEnt.getLevel();
    }

    public void setLevel(int level) {
        roleEnt.setLevel(level);
    }

    public String getAccountId() {
        return roleEnt.getAccountId();
    }

    public void setAccountId(String accountId) {
        roleEnt.setAccountId(accountId);
    }

    public int getExperience() {
        return roleEnt.getExperience();
    }

    public void setExperience(int experience) {
        roleEnt.setExperience(experience);
    }

    public int getX() {
        return roleEnt.getX();
    }

    public void setX(int x) {
        roleEnt.setX(x);
    }

    public int getY() {
        return roleEnt.getY();
    }

    public void setY(int y) {
        roleEnt.setY(y);
    }

    public int getMap() {
        return roleEnt.getMap();
    }
    public void setMap(int map) {
        roleEnt.setMap(map);
    }

    public void setSceneId(int sceneId) {
        roleEnt.setSceneId(sceneId);
    }

    public int getSceneId() {
        return roleEnt.getSceneId();
    }



    public String getName() {
        return roleEnt.getName();
    }

    public void setName(String name) {
        roleEnt.setName(name);
    }

    public static Role valueOf(RoleEnt roleEnt) {
        Role role = new Role();
        role.setRoleEnt(roleEnt);
        return role;
    }




    public RoleAttribute getAttribute() {
        return roleEnt.getAttribute();
    }

    public void setAttribute(RoleAttribute attribute) {
        roleEnt.setAttribute(attribute);
    }


    /**
     * 创建角色时初始化各个模块
     */
    public void createModule() {
        //创建装备栏
        GetBean.getEquipmentService().createEquipmentBar(getId());
        //创建坐骑
        GetBean.getMountService().createMount(getId());
        //创建技能模块
        GetBean.getSkillManager().create(getId());
        //创建buff模块
        GetBean.getBuffManager().create(getId());
    }

    /**
     * 更新属性
     */
    public void updateAttribute(String name, Map<AttributeType, AttributeElement> map) {
        getAttribute().updateModule(RoleAttributesModule.getType(name), map);
    }

    public boolean isChangingMap() {
        return changingMap.get();
    }

    public void setChangingMap(boolean changingMap) {
        this.changingMap.getAndSet(changingMap);
    }

    public boolean getIsTeam() {
        return this.isTeam.get();
    }

    public void setIsTeam(boolean isTeam) {
        this.isTeam.getAndSet(isTeam);
    }

}
