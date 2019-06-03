package com.aiwan.server.role.player.entity;

import com.aiwan.server.ramcache.IEntity;
import com.aiwan.server.ramcache.anno.Cache;
import com.aiwan.server.role.player.model.Role;

import javax.persistence.*;

/**
 * @author dengzebiao
 * 角色实体化类
 * */
@Cache(maxmum = 300)
@Entity()
@Table(name = "role")
public class RoleEnt implements IEntity<Long> {
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long updateTime;

    @Column(nullable = false)
    private Long creatTime;

    @Column(nullable = false)
    private int sex;

    @Column(nullable = false)
    private int job;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false,length = 50)
    private String accountId;

    @Transient
    private Role role;

    public Long getId() {
        return id;
    }

    @Override
    public boolean serialize() {
        return true;
    }

    @Override
    public boolean unserialize() {
        return true;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Long creatTime) {
        this.creatTime = creatTime;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public int getLevel() {
        return level;
    }

    public RoleEnt setLevel(int level) {
        this.level = level;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
