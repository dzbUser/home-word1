package com.aiwan.server.user.role.task.entity;

import com.aiwan.server.ramcache.IEntity;
import com.aiwan.server.ramcache.anno.Cache;
import com.aiwan.server.user.role.task.model.TaskModel;
import com.aiwan.server.util.JsonUtil;

import javax.persistence.*;

/**
 * 任务实体
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
@Cache(maxmum = 300)
@Entity()
@Table(name = "task")
public class TaskEnt implements IEntity<Long> {

    @Id
    @Column(nullable = false)
    private Long rId;

    /**
     * 任务二进制数据
     */
    @Lob
    @Column()
    private byte[] taskData;

    /**
     * 任务实体存储数据类
     */
    @Transient
    private TaskInfo taskInfo = new TaskInfo();

    /**
     * 任务模型类
     */
    @Transient
    private TaskModel taskModel;


    @Override
    public Long getId() {
        return rId;
    }

    @Override
    public boolean serialize() {
        this.taskData = JsonUtil.object2Bytes(taskInfo);
        return true;
    }

    @Override
    public boolean unserialize() {
        taskInfo = (TaskInfo) JsonUtil.bytes2Object(taskData, TaskInfo.class);
        return true;
    }

    @Override
    public void init() {

    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public byte[] getTaskData() {
        return taskData;
    }

    public void setTaskData(byte[] taskData) {
        this.taskData = taskData;
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public TaskModel getTaskModel() {
        return taskModel;
    }

    public void setTaskModel(TaskModel taskModel) {
        this.taskModel = taskModel;
    }
}
