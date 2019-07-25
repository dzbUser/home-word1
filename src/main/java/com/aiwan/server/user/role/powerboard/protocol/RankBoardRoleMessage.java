package com.aiwan.server.user.role.powerboard.protocol;

import java.io.Serializable;

/**
 * 排行榜角色信息
 *
 * @author dengzebiao
 * @since 2019.7.25
 */
public class RankBoardRoleMessage implements Serializable {

    private long rId;

    private String name;

    private long combatPower;

    public static RankBoardRoleMessage valueOf(long rId, String name, long combatPower) {
        RankBoardRoleMessage rankBoardRoleMessage = new RankBoardRoleMessage();
        rankBoardRoleMessage.setCombatPower(combatPower);
        rankBoardRoleMessage.setName(name);
        rankBoardRoleMessage.setrId(rId);
        return rankBoardRoleMessage;
    }

    public long getrId() {
        return rId;
    }

    public void setrId(long rId) {
        this.rId = rId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCombatPower() {
        return combatPower;
    }

    public void setCombatPower(long combatPower) {
        this.combatPower = combatPower;
    }
}
