package com.aiwan.server.user.role.task.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查看可领取任务
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class SM_ViewCanReceiveTask implements Serializable {
    /**
     * 可以领取任务的id列表
     */
    private List<Integer> list = new ArrayList<>();

    public static SM_ViewCanReceiveTask valueOf(List<Integer> list) {
        SM_ViewCanReceiveTask sm_viewCanReceiveTask = new SM_ViewCanReceiveTask();
        sm_viewCanReceiveTask.setList(list);
        return sm_viewCanReceiveTask;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
}
