package com.aiwan.server.role.player.model;

import com.aiwan.server.role.player.entity.RoleEnt;
import com.aiwan.server.role.player.rule.RoleRule;

/**
 * @author dengzebiao
 * 业务逻辑角色操作类
 * */
public class Role {
    private RoleEnt roleEnt;

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


    /**
     * 返回角色信息
     * */
    public String getRoleMessage(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("职业："+ RoleRule.getJob(getJob()));
        if (roleEnt.getSex() == 1){
            buffer.append(" 性别：男");
        }else {
            buffer.append(" 性别：女");
        }
        buffer.append(" 等级："+roleEnt.getLevel());
        buffer.append(" 经验值："+roleEnt.getExperience());
        return buffer.toString();
    }

}
