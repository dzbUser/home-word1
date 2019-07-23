package com.aiwan.server.user.role.task.event;

import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.task.process.TaskProgressType;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务事件类
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public class TaskParam {

    /**
     * 角色
     */
    private Role role;

    /**
     * 事件类型
     */
    private TaskProgressType taskProgressType;


    private Map<String, Integer> paramMap = new HashMap<>();

    public static TaskParam valueOf(Role role, TaskProgressType taskProgressType, Map<String, Integer> paramMap) {
        TaskParam taskParam = new TaskParam();
        taskParam.setRole(role);
        taskParam.setTaskProgressType(taskProgressType);
        taskParam.setParamMap(paramMap);
        return taskParam;
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

    /**
     * 存入参数键值对
     *
     * @param key
     * @param value
     */
    public void putParam(String key, Integer value) {
        paramMap.put(key, value);
    }


    /**
     * 获取对应参数
     *
     * @param key
     * @return
     */
    public Integer getParam(String key) {
        return paramMap.get(key);
    }

    public Map<String, Integer> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Integer> paramMap) {
        this.paramMap = paramMap;
    }
}
