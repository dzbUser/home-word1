package com.aiwan.server.user.role.powerboard.model;

/**
 * 排行榜信息
 *
 * @author dengzebiao
 * @since 2019.7.25
 */
public class RankInfo {

    /**
     * 角色id
     */
    private long rId;

    private long combatPower;

    public static RankInfo valueOf(long rId, long combatPower) {
        RankInfo rankInfo = new RankInfo();
        rankInfo.setCombatPower(combatPower);
        rankInfo.setrId(rId);
        return rankInfo;
    }

    public long getrId() {
        return rId;
    }

    public void setrId(long rId) {
        this.rId = rId;
    }

    public long getCombatPower() {
        return combatPower;
    }

    public void setCombatPower(long combatPower) {
        this.combatPower = combatPower;
    }
}
