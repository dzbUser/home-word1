package com.aiwan.server.user.role.task.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 接受任务协议类
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
@ProtocolAnnotation(protocol = Protocol.RECEIVE_TASK)
public class CM_ReceiveTask implements Serializable {

    private int taskId;

    public static CM_ReceiveTask valueOf(int taskId) {
        CM_ReceiveTask cm_receiveTask = new CM_ReceiveTask();
        cm_receiveTask.setTaskId(taskId);
        return cm_receiveTask;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
