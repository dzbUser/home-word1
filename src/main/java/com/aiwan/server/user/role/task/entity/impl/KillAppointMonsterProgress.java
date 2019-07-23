package com.aiwan.server.user.role.task.entity.impl;

import com.aiwan.server.user.role.task.entity.AbstractProgressElement;
import com.aiwan.server.user.role.task.process.TaskProgressType;

/**
 * 击杀指定怪物进行数据元素
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class KillAppointMonsterProgress extends AbstractProgressElement {

    /**
     * 需要击杀的怪物id
     */
    private int monsterId;

    public KillAppointMonsterProgress(TaskProgressType taskProgressType, int value, int finishValue, int monsterId) {
        super(taskProgressType, value, finishValue);
        this.monsterId = monsterId;
    }

    public int getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(int monsterId) {
        this.monsterId = monsterId;
    }
}
