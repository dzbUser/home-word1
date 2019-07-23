package com.aiwan.server.user.role.task.event.impl;

import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.task.event.AbstractTaskParam;
import com.aiwan.server.user.role.task.process.TaskProgressType;

/**
 * 击杀指定怪物任务参数
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class KillAppointMonsterParam extends AbstractTaskParam {

    private int monsterId;

    public KillAppointMonsterParam(Role role, TaskProgressType taskProgressType, int monsterId) {
        super(role, taskProgressType);
        this.monsterId = monsterId;
    }

    public int getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(int monsterId) {
        this.monsterId = monsterId;
    }
}
