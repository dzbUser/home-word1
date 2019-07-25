package com.aiwan.server.user.role.task.model;

import com.aiwan.server.user.role.task.entity.AbstractProgressElement;
import com.aiwan.server.user.role.task.entity.TaskElement;
import com.aiwan.server.user.role.task.entity.TaskEnt;
import com.aiwan.server.user.role.task.process.AbstractProcessor;
import com.aiwan.server.user.role.task.process.TaskProgressType;
import com.aiwan.server.user.role.task.resource.CompleteCondition;
import com.aiwan.server.user.role.task.resource.TaskResource;
import com.aiwan.server.util.BaseConfiguration;
import com.aiwan.server.util.GetBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务模型
 */
public class TaskModel {

    /**
     * 任务实体
     */
    private TaskEnt taskEnt;

    /**
     * 获取对应id的任务元素
     *
     * @param taskId 任务id
     * @return
     */
    public TaskElement getTaskElement(int taskId) {
        return taskEnt.getTaskInfo().getTaskElementMap().get(taskId);
    }

    /**
     * 获取可以接受的任务（暂用）
     *
     * @return
     */
    public List<Integer> getCanReceiveTask() {
        List<Integer> list = new ArrayList<>();
        List<Integer> finishTask = taskEnt.getTaskInfo().getFinishTaskId();
        //若完成的任务是空的
        if (finishTask.size() == 0) {
            if (getTaskElement(BaseConfiguration.ORIGIN_TASK) == null) {
                //添加初始任务
                list.add(BaseConfiguration.ORIGIN_TASK);
            }
            return list;
        }
        for (Integer taskId : taskEnt.getTaskInfo().getFinishTaskId()) {
            //获取已完成任务资源
            TaskResource taskResource = GetBean.getTaskManager().getTaskResource(taskId);
            //获取下一个任务
            int nextTaskId = taskResource.getNextTaskId();
            if (nextTaskId == 0) {
                //没有下一个任务
                continue;
            }
            if (getTaskElement(nextTaskId) == null && !finishTask.contains(nextTaskId)) {
                //该任务未接受且未完成
                list.add(nextTaskId);
            }
        }
        return list;
    }

    /**
     * 查看任务是否已经完成
     *
     * @param taskId
     */
    public boolean isFinished(int taskId) {
        for (Integer task : taskEnt.getTaskInfo().getFinishTaskId()) {
            if (task == taskId) {
                return true;
            }
        }
        return false;
    }

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

    /**
     * 创建任务
     *
     * @param taskId 任务id
     */
    public void createTask(int taskId, TaskResource taskResource) {
        TaskElement taskElement = new TaskElement();
        //设置任务id
        taskElement.setTaskId(taskId);
        //循环创建任务条件
        for (CompleteCondition completeCondition : taskResource.getCompleteConditions()) {
            //获取类型
            TaskProgressType progressType = completeCondition.getTaskProgressType();
            //创建任务进度元素类
            AbstractProgressElement taskProgressElement = progressType.shiftParam(completeCondition.getParam(), getTaskEnt().getrId());
            //初始化任务状态
            AbstractProcessor abstractProcessor = AbstractProcessor.getProcessor(progressType);
            abstractProcessor.initExcuteProgress(taskProgressElement, getTaskEnt().getrId());
            taskElement.getTaskProgress().add(taskProgressElement);
        }
        //检查是否完成
        taskElement.examineFinish();
        this.getTaskEnt().getTaskInfo().getTaskElementMap().put(taskElement.getTaskId(), taskElement);
    }

    /**
     * 完成任务
     *
     * @param taskId 任务id
     */
    public void completeTask(int taskId) {
        TaskElement taskElement = getTaskElement(taskId);
        if (taskElement != null && taskElement.isCanFinish()) {
            getTaskEnt().getTaskInfo().getFinishTaskId().add(taskElement.getTaskId());
            getTaskEnt().getTaskInfo().getTaskElementMap().remove(taskElement.getTaskId());
        }
    }
}
