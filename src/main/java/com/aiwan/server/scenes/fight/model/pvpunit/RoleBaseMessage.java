package com.aiwan.server.scenes.fight.model.pvpunit;

import com.aiwan.server.scenes.model.Position;

public class RoleBaseMessage {

    /**
     * 所属账号
     */
    private String accountId;

    /**
     * 角色id
     */
    private Long rId;

    /**
     * 角色名字
     */
    private String name;

    /**
     * 角色位置
     */
    private Position position;

    public static RoleBaseMessage valueOf(String accountId, Long rId, Position position, String name) {
        RoleBaseMessage roleBaseMessage = new RoleBaseMessage();
        roleBaseMessage.setAccountId(accountId);
        roleBaseMessage.setPosition(position);
        roleBaseMessage.setName(name);
        return roleBaseMessage;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
