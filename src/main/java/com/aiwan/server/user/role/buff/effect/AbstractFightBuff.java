package com.aiwan.server.user.role.buff.effect;

import com.aiwan.server.user.role.fight.pvpunit.BaseUnit;
import com.aiwan.server.user.role.buff.resource.EffectResource;
import com.aiwan.server.util.GetBean;

/**
 * buff效果抽象类
 *
 * @author dengzebiao
 * @since 2019.7.11
 */
public abstract class AbstractFightBuff {

    /**
     * 对应buff的id
     */
    private int effectId;

    /**
     * 开始时间
     */
    private Long gainTime;

    /**
     * 结束时间时间
     */
    private Long endTime;

    /**
     * 下一次作用时间
     */
    private Long workTime;

    /**
     * 周期时间
     */
    private Long period;

    /**
     * 施法buff单位
     */
    private BaseUnit activeUnit;


    /**
     * buff生效
     * @param passive buff接收方
     */
    public abstract void doActive(BaseUnit passive);


    /**
     * buff失效后的操作
     */
    public boolean isAttributeBuff() {
        return false;
    }

    /**
     * 初始化
     *
     * @param effectId   id
     * @param gainTime   获取时间
     * @param period     周期
     * @param duration   持续时间
     * @param activeUnit 施法单位
     */
    public void init(int effectId, long gainTime, long period, long duration, long workTime, BaseUnit activeUnit) {
        setActiveUnit(activeUnit);
        setEffectId(effectId);
        setEndTime(gainTime + duration);
        setPeriod(period);
        setWorkTime(workTime);
        setGainTime(gainTime);
    }


    /**
     * 获取buff效果资源
     */
    public EffectResource getEffectResource() {
        return GetBean.getBuffManager().getEffectResource(getEffectId());
    }


    public int getEffectId() {
        return effectId;
    }

    public void setEffectId(int effectId) {
        this.effectId = effectId;
    }

    public Long getGainTime() {
        return gainTime;
    }

    public void setGainTime(Long gainTime) {
        this.gainTime = gainTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Long workTime) {
        this.workTime = workTime;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public BaseUnit getActiveUnit() {
        return activeUnit;
    }

    public void setActiveUnit(BaseUnit activeUnit) {
        this.activeUnit = activeUnit;
    }

}
