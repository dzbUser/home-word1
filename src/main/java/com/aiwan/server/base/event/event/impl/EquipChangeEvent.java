package com.aiwan.server.base.event.event.impl;

import com.aiwan.server.base.event.event.IEvent;
import com.aiwan.server.user.role.player.model.Role;

/**
 * 装备修改事件
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class EquipChangeEvent implements IEvent {

    private Role role;

    public static EquipChangeEvent valueOf(Role role) {
        EquipChangeEvent equipChangeEvent = new EquipChangeEvent();
        equipChangeEvent.setRole(role);
        return equipChangeEvent;
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
