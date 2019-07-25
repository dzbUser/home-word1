package com.aiwan.server.user.role.powerboard;

import com.aiwan.server.base.event.core.EventId;
import com.aiwan.server.base.event.event.IEvent;
import com.aiwan.server.user.role.player.model.Role;

/**
 * 战力榜更新事件
 *
 * @author dengzebiao
 * @since 2019.7.25
 */
public class RankRefreshEvent implements IEvent {

    /**
     * 改变角色
     */
    private Role role;

    public RankRefreshEvent(Role role) {
        this.role = role;
    }

    @Override
    public long getOwner() {
        return EventId.RANK_EVENT.getId();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
