package com.aiwan.server.base.event.event.impl;

import com.aiwan.server.base.event.event.IEvent;
import com.aiwan.server.user.role.player.model.Role;

/**
 * 玩家升级事件
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class RoleUpgradeEvent implements IEvent {

    private Role role;

    public static RoleUpgradeEvent valueOf(Role role) {
        RoleUpgradeEvent roleUpgradeEvent = new RoleUpgradeEvent();
        roleUpgradeEvent.setRole(role);
        return roleUpgradeEvent;
    }

    @Override
    public long getOwner() {
        return role.getId();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
