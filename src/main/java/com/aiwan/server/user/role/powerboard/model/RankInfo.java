package com.aiwan.server.user.role.powerboard.model;

import java.io.Serializable;

/**
 * 排行榜信息
 *
 * @author dengzebiao
 * @since 2019.7.25
 */
public class RankInfo implements Comparable<RankInfo> {

    /**
     * 角色id
     */
    private Long rId;

    /**
     * 用户排序的战力
     */
    private long combatPower;

    private String name;

    private long refreshTime;

    public static RankInfo valueOf(long rId, long combatPower, long refreshTime, String name) {
        RankInfo rankInfo = new RankInfo();
        rankInfo.setCombatPower(combatPower);
        rankInfo.setrId(rId);
        rankInfo.setRefreshTime(refreshTime);
        rankInfo.setName(name);
        return rankInfo;
    }

    /**
     * 重写比较函数
     *
     * @param info
     * @return
     */
    @Override
    public int compareTo(RankInfo info) {
        if (this.getCombatPower() > info.getCombatPower()) {
            return -1;
        } else if (this.getCombatPower() == info.getCombatPower()) {
            if (this.refreshTime < info.getRefreshTime()) {
                return -1;
            } else if (this.refreshTime == info.refreshTime) {
                return this.rId.compareTo(info.getrId());
            }
        }
        return 1;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public long getCombatPower() {
        return combatPower;
    }

    public void setCombatPower(long combatPower) {
        this.combatPower = combatPower;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
