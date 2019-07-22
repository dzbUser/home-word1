package com.aiwan.server.user.role.task.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务数据实体类
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public class TaskInfo {

    /**
     * 已完成任务
     */
    private List<Integer> finishTaskId = new ArrayList<>();

    /**
     * 正在进行的任务
     */
    private Map<Integer, TaskElement> taskElementMap = new HashMap<>();


    public List<Integer> getFinishTaskId() {
        return finishTaskId;
    }

    public void setFinishTaskId(List<Integer> finishTaskId) {
        this.finishTaskId = finishTaskId;
    }

    public Map<Integer, TaskElement> getTaskElementMap() {
        return taskElementMap;
    }

    public void setTaskElementMap(Map<Integer, TaskElement> taskElementMap) {
        this.taskElementMap = taskElementMap;
    }
}
