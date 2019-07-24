package com.aiwan.server.user.role.task.entity.impl;

import com.aiwan.server.user.role.task.entity.AbstractProgressElement;
import com.aiwan.server.user.role.task.process.TaskProgressType;
/**
 * 普通进度数据元素，没有属于自己的参数
 */
public class CommonProgress extends AbstractProgressElement {

    public CommonProgress(TaskProgressType taskProgressType, int value, int finishValue) {
        super(taskProgressType, value, finishValue);
    }

    public CommonProgress() {

    }
}
