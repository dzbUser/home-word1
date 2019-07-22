package com.aiwan.server.user.role.task.entity;

import com.aiwan.server.user.role.task.process.TaskProgressType;

/**
 * 任务进度元素
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public class TaskProgressElement {

    private TaskProgressType taskProgressType;

    private int value;

    private boolean isFinish = false;

    public static TaskProgressElement valyeOf(TaskProgressType taskProgressType, int value) {
        TaskProgressElement taskProgressElement = new TaskProgressElement();
        taskProgressElement.setTaskProgressType(taskProgressType);
        taskProgressElement.setValue(value);
        return taskProgressElement;
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
}
