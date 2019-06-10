package com.aiwan.server.user.role.equipment.entity;

import com.aiwan.server.ramcache.IEntity;
import com.aiwan.server.ramcache.anno.Cache;
import com.aiwan.server.user.role.equipment.model.EquipmentInfo;
import com.aiwan.server.util.JsonUtil;

import javax.persistence.*;

/**
 * @author dengzebiao
 * 角色实体化类
 * */
@Cache(maxmum = 300)
@Entity()
@Table(name = "equipment")
public class EquipmentEntity implements IEntity<Long> {
    /** 角色id */
    @Id
    @Column(nullable = false)
    private Long id;

    /** 创建时间 */
    @Column(nullable = false)
    private Long createTime;

    /** 更新时间 */
    @Column(nullable = false)
    private Long updateTime;

    /** 装备栏二进制数据 */
    @Lob
    @Column(nullable = false)
    private byte[] equipmentData;

    /** 装备栏详细数据 */
    @Transient
    private EquipmentInfo equipmentInfo = new EquipmentInfo();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean serialize() {
        equipmentData = JsonUtil.object2Bytes(equipmentInfo);
        return true;
    }

    @Override
    public boolean unserialize() {
        equipmentInfo = (EquipmentInfo) JsonUtil.bytes2Object(equipmentData,EquipmentInfo.class);
        return true;
    }

    @Override
    public void init() {

    }

    public EquipmentEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public EquipmentEntity setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public EquipmentEntity setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public EquipmentInfo getEquipmentInfo() {
        return equipmentInfo;
    }

    public EquipmentEntity setEquipmentInfo(EquipmentInfo equipmentInfo) {
        this.equipmentInfo = equipmentInfo;
        return this;
    }
}
