package com.aiwan.server.user.role.task.model;

import com.aiwan.server.user.role.task.entity.TaskEnt;

/**
 * 任务模型
 */
public class TaskModel {

    /**
     * 任务实体
     */
    private TaskEnt taskEnt;

    public static TaskModel valueOf(TaskEnt taskEnt) {
        TaskModel taskModel = new TaskModel();
        taskModel.setTaskEnt(taskEnt);
        return taskModel;
    }

    public TaskEnt getTaskEnt() {
        return taskEnt;
    }

    public void setTaskEnt(TaskEnt taskEnt) {
        this.taskEnt = taskEnt;
    }
}
