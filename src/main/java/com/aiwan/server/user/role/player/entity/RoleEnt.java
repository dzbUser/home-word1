package com.aiwan.server.user.role.player.entity;

import com.aiwan.server.ramcache.IEntity;
import com.aiwan.server.ramcache.anno.Cache;
import com.aiwan.server.user.role.attributes.model.impl.RoleAttribute;
import com.aiwan.server.user.role.player.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

/**
 * @author dengzebiao
 * 角色实体化类
 * */
@Cache(maxmum = 300)
@Entity()
@Table(name = "role")
public class RoleEnt implements IEntity<Long> {

    @Transient
    Logger logger = LoggerFactory.getLogger(RoleEnt.class);

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

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private int y;

    @Column(nullable = false)
    private int map;

    @Column(nullable = false,length = 50)
    private String accountId;

    /**
     * 角色名字
     */
    @Column(nullable = false, columnDefinition = "varchar(20) character set utf8 collate utf8_bin comment '角色名字'")
    private String name;

    /** 经验值 */
    @Column(nullable = false)
    private int experience;

    /** 人物属性 */
    @Transient
    private RoleAttribute roleAttribute;

    @Transient
    private Role role;

    @Override
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

    @Override
    public void init() {
        roleAttribute = new RoleAttribute(getId());
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

    public int getExperience() {
        return experience;
    }

    public RoleEnt setExperience(int experience) {
        this.experience = experience;
        return this;
    }

    public RoleAttribute getAttribute() {
        return roleAttribute;
    }

    public RoleEnt setAttribute(RoleAttribute attribute) {
        this.roleAttribute = attribute;
        return this;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
