package com.aiwan.server.user.role.buff.model;

import com.aiwan.server.scenes.fight.model.pvpunit.BaseUnit;
import com.aiwan.server.user.role.buff.resource.EffectResource;
import com.aiwan.server.user.role.skill.impact.ImpactInterface;
import com.aiwan.server.user.role.skill.impact.ImpactType;
import com.aiwan.server.util.GetBean;

/**
 * buff效果抽象类
 */
public class BuffEffect {

    /**
     * 对应buffid
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
     * 施加buff单位
     */
    private BaseUnit activeUnit;


    /**
     * buff生效
     */
    public void doActive(BaseUnit passive) {
        getImpact().takeImpact(getActiveUnit(), passive, getEffectResource().getValue());
    }

    /**
     * 获取buff效果资源
     */
    public EffectResource getEffectResource() {
        return GetBean.getBuffManager().getEffectResource(getEffectId());
    }

    /**
     * 获取对应技能效果
     */
    public ImpactInterface getImpact() {
        return ImpactType.getImpactType(getEffectResource().getImpactId()).creator();
    }

    public static BuffEffect valueOf(int effectId, long gainTime, long period, long duration, BaseUnit activeUnit) {
        BuffEffect buffEffect = new BuffEffect();
        buffEffect.setActiveUnit(activeUnit);
        buffEffect.setEffectId(effectId);
        buffEffect.setEndTime(gainTime + duration);
        buffEffect.setPeriod(period);
        buffEffect.setWorkTime(gainTime);
        buffEffect.setGainTime(gainTime);
        return buffEffect;
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
