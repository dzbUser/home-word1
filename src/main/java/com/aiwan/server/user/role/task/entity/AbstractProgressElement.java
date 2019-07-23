package com.aiwan.server.user.role.task.entity;

import com.aiwan.server.user.role.task.process.TaskProgressType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Map;

/**
 * 抽象进度元素
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
public abstract class AbstractProgressElement {

    private TaskProgressType taskProgressType;

    private int value;

    private int finishValue;

    private boolean isFinish = false;

    public AbstractProgressElement(TaskProgressType taskProgressType, int value, int finishValue) {
        this.taskProgressType = taskProgressType;
        this.value = value;
        this.finishValue = finishValue;
    }

    public TaskProgressType getTaskProgressType() {
        return taskProgressType;
    }

    public void setTaskProgressType(TaskProgressType taskProgressType) {
        this.taskProgressType = taskProgressType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public int getFinishValue() {
        return finishValue;
    }

    public void setFinishValue(int finishValue) {
        this.finishValue = finishValue;
    }
}
