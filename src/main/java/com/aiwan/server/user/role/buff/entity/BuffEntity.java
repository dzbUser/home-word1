package com.aiwan.server.user.role.buff.entity;

import com.aiwan.server.ramcache.IEntity;
import com.aiwan.server.ramcache.anno.Cache;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.JsonUtil;

import javax.persistence.*;

/**
 * buff实体
 */
@Cache(maxmum = 300)
@Entity()
@Table(name = "buff")
public class BuffEntity implements IEntity<Long> {

    /**
     * 角色id
     */
    @Id
    @Column(nullable = false)
    private Long rId;

    /**
     * Buff二进制数据
     */
    @Lob
    @Column()
    private byte[] buffData;

    /**
     * Buff详细数据
     */
    @Transient
    private BuffInfo buffInfo = new BuffInfo();


    @Override
    public Long getId() {
        return rId;
    }

    @Override
    public boolean serialize() {
        buffData = JsonUtil.object2Bytes(buffInfo);
        return true;
    }

    @Override
    public boolean unserialize() {
        buffInfo = (BuffInfo) JsonUtil.bytes2Object(buffData, BuffInfo.class);
        return true;
    }

    @Override
    public void init() {
        //检查所有buff，若结束时间小于当前时间，去除该buff
        //获取当前时间
        for (BuffElement buffElement : getBuffInfo().getMap().values()) {
            Long currentTime = System.currentTimeMillis();
            if (buffElement.getOverTime() <= currentTime) {
                removeBuff(buffElement.getBuffId());
            } else {
                //若没有对应的buff定时，应添加对应定时器
                Long delay = buffElement.getOverTime() - currentTime;
                GetBean.getBuffService().refreshCommand(rId, buffElement.getBuffId(), delay);
            }
        }
    }

    public BuffInfo getBuffInfo() {
        return buffInfo;
    }

    public void setBuffInfo(BuffInfo buffInfo) {
        this.buffInfo = buffInfo;
    }

    /**
     * 获取buff
     */
    public BuffElement getBuff(int buffId) {
        return buffInfo.getBuff(buffId);
    }

    /**
     * 移除buff
     */
    public void removeBuff(int buffId) {
        buffInfo.removeBuff(buffId);
    }

    /**
     * 添加buff
     */
    public void putBuff(BuffElement buffElement) {
        buffInfo.getMap().put(buffElement.getBuffId(), buffElement);
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }
}
