package com.aiwan.server.base.event.event.impl;

import com.aiwan.server.base.event.event.IEvent;
import com.aiwan.server.user.role.player.model.Role;

/**
 * 怪物击杀事件
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class MonsterKillEvent implements IEvent {

    private Role role;

    private int monsterId;

    public static MonsterKillEvent valueOf(Role role, int monsterId) {
        MonsterKillEvent monsterKillEvent = new MonsterKillEvent();
        monsterKillEvent.setMonsterId(monsterId);
        monsterKillEvent.setRole(role);
        return monsterKillEvent;
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

    public int getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(int monsterId) {
        this.monsterId = monsterId;
    }
}
