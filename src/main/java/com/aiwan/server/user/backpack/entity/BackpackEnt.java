package com.aiwan.server.user.backpack.entity;


import com.aiwan.server.ramcache.IEntity;
import com.aiwan.server.ramcache.anno.Cache;
import com.aiwan.server.user.backpack.model.BackpackInfo;
import com.aiwan.server.user.backpack.model.BackpackItem;
import com.aiwan.server.util.JsonUtil;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.4
 * 背包实体
 * */
@Cache(maxmum = 300)
@Entity()
@Table(name = "Backpacktable")
public class BackpackEnt implements IEntity<String> {

    /** 用户账号 */
    @Id
    @Column(nullable = false,length = 50)
    private String accountId;

    /** 创建时间 */
    @Column(nullable = false)
    private Long createTime;

    /** 更新时间 */
    @Column(nullable = false)
    private Long updateTime;

    /** 背包大小 */
    @Column(nullable = false)
    private int maxNum;

    /** 背包二进制数据 */
    @Lob
    @Column()
    private byte[] backpackData;

    /** 背包详细数据，用户创建时需要初始化背包*/
    @Transient
    private BackpackInfo backpackInfo;


    @Override
    public String getId() {
        return accountId;
    }

    @Override
    public boolean serialize() {
        backpackData = JsonUtil.object2Bytes(backpackInfo);
        return true;
    }

    @Override
    public boolean unserialize() {
        backpackInfo = (BackpackInfo) JsonUtil.bytes2Object(backpackData,BackpackInfo.class);
        return true;
    }

    @Override
    public void init() {

    }

    public String getAccountId() {
        return accountId;
    }

    public BackpackEnt setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public BackpackEnt setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public BackpackEnt setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public BackpackEnt setMaxNum(int maxNum) {
        this.maxNum = maxNum;
        return this;
    }

    public BackpackInfo getBackpackInfo() {
        return backpackInfo;
    }

    public BackpackEnt setBackpackInfo(BackpackInfo backpackInfo) {
        this.backpackInfo = backpackInfo;
        return this;
    }
}
