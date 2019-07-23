package com.aiwan.server.user.role.task.service;

/**
 * 任务业务类接口
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public interface ITaskService {

    /**
     * 查看可以领取的任务
     *
     * @param rId 角色id
     */
    void viewCanReceiveTask(long rId);

    /**
     * 接受任务
     *
     * @param taskId 任务id
     * @param rId    角色id
     */
    void receiveTask(int taskId, long rId);

    /**
     * 查看进行中的任务
     *
     * @param rId
     */
    void viewProcessingTask(long rId);

    /**
     * 完成任务
     *
     * @param rId    角色id
     * @param taskId 任务id
     */
    void completeTask(long rId, int taskId);
}
