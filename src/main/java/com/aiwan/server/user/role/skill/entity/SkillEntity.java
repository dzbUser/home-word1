package com.aiwan.server.user.role.skill.entity;

import com.aiwan.server.ramcache.IEntity;
import com.aiwan.server.ramcache.anno.Cache;
import com.aiwan.server.user.backpack.model.BackpackInfo;
import com.aiwan.server.user.role.skill.model.SkillInfo;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.JsonUtil;

import javax.persistence.*;

/**
 * @author dengzebiao
 * @since 2019.7.1
 * 技能实体类
 */
@Cache(maxmum = 300)
@Entity()
@Table(name = "skill")
public class SkillEntity implements IEntity<Long> {
    /**
     * 角色id
     */
    @Id
    @Column(nullable = false)
    private Long rId;

    /**
     * 背包二进制数据
     */
    @Lob
    @Column()
    private byte[] skillData;

    /**
     * 背包详细数据
     */
    @Transient
    private SkillInfo skillInfo;


    @Override
    public Long getId() {
        return rId;
    }

    @Override
    public boolean serialize() {
        skillData = JsonUtil.object2Bytes(skillInfo);
        return true;
    }

    @Override
    public boolean unserialize() {
        skillInfo = (SkillInfo) JsonUtil.bytes2Object(skillData, SkillInfo.class);
        return true;
    }

    @Override
    public void init() {

    }

    public Long getRId() {
        return rId;
    }

    public void setRId(Long rId) {
        this.rId = rId;
    }

    public byte[] getSkillData() {
        return skillData;
    }

    public void setSkillData(byte[] skillData) {
        this.skillData = skillData;
    }

    public SkillInfo getSkillInfo() {
        return skillInfo;
    }

    public void setSkillInfo(SkillInfo skillInfo) {
        this.skillInfo = skillInfo;
    }

    /**
     * 获取最大技能栏数
     */
    public int getMaxSkillBarNum() {
        return GetBean.getRoleResourceManager().getRoleResource().getMaxSkillNum();
    }
}
