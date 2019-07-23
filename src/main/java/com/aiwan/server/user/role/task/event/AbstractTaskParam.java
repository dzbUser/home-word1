package com.aiwan.server.user.role.task.event;

import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.task.process.TaskProgressType;

/**
 * 抽象任务参数
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class AbstractTaskParam {
    /**
     * 角色
     */
    private Role role;

    /**
     * 事件类型
     */
    private TaskProgressType taskProgressType;

    public AbstractTaskParam(Role role, TaskProgressType taskProgressType) {
        this.role = role;
        this.taskProgressType = taskProgressType;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public TaskProgressType getTaskProgressType() {
        return taskProgressType;
    }

    public void setTaskProgressType(TaskProgressType taskProgressType) {
        this.taskProgressType = taskProgressType;
    }
}
