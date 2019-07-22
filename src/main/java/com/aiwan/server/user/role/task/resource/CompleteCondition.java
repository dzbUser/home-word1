package com.aiwan.server.user.role.task.resource;

import com.aiwan.server.user.role.task.process.TaskProgressType;

/**
 * 完成条件
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public class CompleteCondition {

    /**
     * 类型
     */
    private TaskProgressType taskProgressType;

    /**
     * 参数
     */
    private String param;

    public static CompleteCondition valueOf(TaskProgressType taskProgressType, String param) {
        CompleteCondition completeCondition = new CompleteCondition();
        completeCondition.setParam(param);
        completeCondition.setTaskProgressType(taskProgressType);
        return completeCondition;
    }

    public TaskProgressType getTaskProgressType() {
        return taskProgressType;
    }

    public void setTaskProgressType(TaskProgressType taskProgressType) {
        this.taskProgressType = taskProgressType;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
