package com.aiwan.server.user.role.task.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务最小单元实体
 */
public class TaskElement {

    /**
     * 任务id
     */
    private int taskId;

    /**
     * 任务进度
     */
    private List<AbstractProgressElement> taskProgress = new ArrayList<>();

    /**
     * 是否可以完成
     */
    private boolean isCanFinish = false;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public List<AbstractProgressElement> getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(List<AbstractProgressElement> taskProgress) {
        this.taskProgress = taskProgress;
    }

    public boolean isCanFinish() {
        return isCanFinish;
    }

    public void setCanFinish(boolean canFinish) {
        isCanFinish = canFinish;
    }

    /**
     * 查看是否完成
     */
    public void examineFinish() {
        for (AbstractProgressElement taskProgressElement : taskProgress) {
            if (!taskProgressElement.isFinish()) {
                return;
            }
        }
        setCanFinish(true);
    }
}
