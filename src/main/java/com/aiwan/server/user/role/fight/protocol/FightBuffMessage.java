package com.aiwan.server.user.role.fight.protocol;

import java.io.Serializable;

/**
 * 战斗buff信息
 */
public class FightBuffMessage implements Serializable {

    /**
     * buffid
     */
    private int effectId;

    /**
     * 结束时间
     */
    private long overTime;

    public static FightBuffMessage valueOf(int effectId, long overTime) {
        FightBuffMessage fightBuffMessage = new FightBuffMessage();
        fightBuffMessage.setEffectId(effectId);
        fightBuffMessage.setOverTime(overTime);
        return fightBuffMessage;
    }

    public int getEffectId() {
        return effectId;
    }

    public void setEffectId(int effectId) {
        this.effectId = effectId;
    }

    public long getOverTime() {
        return overTime;
    }

    public void setOverTime(long overTime) {
        this.overTime = overTime;
    }
}
