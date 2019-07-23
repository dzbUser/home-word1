package com.aiwan.server.user.role.task.protocol;


import java.io.Serializable;
import java.util.List;

/**
 * 查看进行中的任务
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class SM_ViewProcessingTask implements Serializable {

    private List<TaskElementMessage> list;

    public static SM_ViewProcessingTask valueOf(List<TaskElementMessage> list) {
        SM_ViewProcessingTask sm_viewProcessingTask = new SM_ViewProcessingTask();
        sm_viewProcessingTask.setList(list);
        return sm_viewProcessingTask;
    }

    public List<TaskElementMessage> getList() {
        return list;
    }

    public void setList(List<TaskElementMessage> list) {
        this.list = list;
    }
}
