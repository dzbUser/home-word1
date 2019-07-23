package com.aiwan.server.user.role.task.protocol;

import java.io.Serializable;

/**
 * 任务进度协议类
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class TaskProgessMessage implements Serializable {

    /**
     * 当前值
     */
    private int value;

    /**
     * 完成值
     */
    private int finishValue;

    public static TaskProgessMessage valueOf(int value, int finishValue) {
        TaskProgessMessage taskProgessMessage = new TaskProgessMessage();
        taskProgessMessage.setFinishValue(finishValue);
        taskProgessMessage.setValue(value);
        return taskProgessMessage;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getFinishValue() {
        return finishValue;
    }

    public void setFinishValue(int finishValue) {
        this.finishValue = finishValue;
    }
}
