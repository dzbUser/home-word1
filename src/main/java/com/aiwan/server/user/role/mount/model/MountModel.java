package com.aiwan.server.user.role.mount.model;

import com.aiwan.server.user.role.mount.entity.MountEntity;

/**
 * @author dengzebiao
 * @since 2019.6.6
 * 业务实体类
 * */
public class MountModel {
    private MountEntity mountEntity;

    public MountEntity getMountEntity() {
        return mountEntity;
    }

    public MountModel setMountEntity(MountEntity mountEntity) {
        this.mountEntity = mountEntity;
        return this;
    }

    public void setId(Long id) {
        mountEntity.setId(id);
    }

    public int getLevel() {
        return mountEntity.getLevel();
    }

    public void setLevel(int level) {
        mountEntity.setLevel(level);
    }

    public int getExperience() {
        return mountEntity.getExperience();
    }

    public void setExperience(int experience) {
        mountEntity.setExperience(experience);
    }
}
