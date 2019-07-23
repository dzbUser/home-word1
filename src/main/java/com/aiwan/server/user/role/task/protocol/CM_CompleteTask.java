package com.aiwan.server.user.role.task.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 完成任务
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
@ProtocolAnnotation(protocol = Protocol.COMPLETE_TASK)
public class CM_CompleteTask implements Serializable {

    /**
     * 完成的任务id
     */
    private int taskId;

    public static CM_CompleteTask valueOf(int taskId) {
        CM_CompleteTask cm_completeTask = new CM_CompleteTask();
        cm_completeTask.setTaskId(taskId);
        return cm_completeTask;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
