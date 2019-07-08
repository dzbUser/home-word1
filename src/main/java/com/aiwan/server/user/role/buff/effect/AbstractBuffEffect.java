package com.aiwan.server.user.role.buff.effect;

import com.aiwan.server.monster.model.Monster;
import com.aiwan.server.scenes.fight.model.pvpunit.FighterRole;

/**
 * buff效果抽象类
 */
public abstract class AbstractBuffEffect {

    /**
     * 对应buffid
     */
    private int buffId;

    /**
     * 开始时间
     */
    private Long gainTime;

    /**
     * 持续时间
     */
    private Long sustainTime;

    /**
     * 周期
     */
    private Long period;

    /**
     * 所属角色
     */
    private Long rId;

    /**
     * 玩家生效
     */
    public abstract void doActive(FighterRole role);

    /**
     * 怪物生效
     */
    public abstract void doActive(Monster monster);

    public int getBuffId() {
        return buffId;
    }

    public void setBuffId(int buffId) {
        this.buffId = buffId;
    }

    public Long getGainTime() {
        return gainTime;
    }

    public void setGainTime(Long gainTime) {
        this.gainTime = gainTime;
    }

    public Long getSustainTime() {
        return sustainTime;
    }

    public void setSustainTime(Long sustainTime) {
        this.sustainTime = sustainTime;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }
}
