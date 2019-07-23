package com.aiwan.server.user.role.task.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 任务元素信息类
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class TaskElementMessage implements Serializable {

    /**
     * 任务id
     */
    private int taskId;

    private List<TaskProgessMessage> list;

    public static TaskElementMessage valueOf(int taskId, List<TaskProgessMessage> list) {
        TaskElementMessage taskElementMessage = new TaskElementMessage();
        taskElementMessage.setList(list);
        taskElementMessage.setTaskId(taskId);
        return taskElementMessage;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public List<TaskProgessMessage> getList() {
        return list;
    }

    public void setList(List<TaskProgessMessage> list) {
        this.list = list;
    }
}
