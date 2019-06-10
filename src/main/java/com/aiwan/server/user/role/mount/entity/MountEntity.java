package com.aiwan.server.user.role.mount.entity;

import com.aiwan.server.ramcache.IEntity;
import com.aiwan.server.ramcache.anno.Cache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author dengzebiao
 * @since 2019.6.6
 * 坐骑实体类
 * */
@Cache(maxmum = 300)
@Entity()
@Table(name = "mount")
public class MountEntity implements IEntity<Long> {
    /** 角色id，唯一标识 */
    @Id
    @Column(nullable = false)
    private Long id;

    /** 等级 */
    @Column(nullable = false)
    private int level;

    /** 经验值 */
    @Column(nullable = false)
    private int experience;

    /** 创建对象 */
    public static MountEntity getInitValue(Long rId){
        MountEntity mountEntity = new MountEntity();
        mountEntity.setLevel(0);
        mountEntity.setExperience(0);
        mountEntity.setId(rId);
        return mountEntity;
    }
    @Override
    public Long getId() {
        return null;
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

    }

    public MountEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public MountEntity setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getExperience() {
        return experience;
    }

    public MountEntity setExperience(int experience) {
        this.experience = experience;
        return this;
    }
}
