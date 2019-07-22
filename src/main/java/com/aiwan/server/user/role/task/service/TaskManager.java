package com.aiwan.server.user.role.task.service;

import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.ramcache.service.impl.EntityCacheServiceImpl;
import com.aiwan.server.user.role.task.entity.TaskEnt;
import com.aiwan.server.user.role.task.model.TaskModel;
import com.aiwan.server.user.role.task.resource.TaskResource;
import com.aiwan.server.util.ExcelUtil;
import com.aiwan.server.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务管理
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
@Manager
public class TaskManager {

    private final static Logger logger = LoggerFactory.getLogger(TaskManager.class);

    @Static(initializeMethodName = "initTaskResource")
    private Map<Integer, TaskResource> taskResourceMap = new HashMap<>();

    /**
     * 缓存
     */
    private EntityCacheServiceImpl<Long, TaskEnt> cache;

    /**
     * 获取任务模型
     */
    public TaskModel load(Long id) {
        TaskEnt taskEnt = cache.load(id);
        if (taskEnt == null) {
            return null;
        }
        TaskModel taskModel = taskEnt.getTaskModel();
        if (taskModel == null) {
            taskModel = TaskModel.valueOf(taskEnt);
            taskEnt.setTaskModel(taskModel);
        }
        return taskModel;
    }

    /**
     * 保存任务模型
     */
    public void save(TaskModel taskModel) {
        TaskEnt taskEnt = taskModel.getTaskEnt();
        cache.writeBack(taskEnt.getId(), taskEnt);
    }

    /**
     * 创建任务模型
     */
    public void create(Long rId) {
        TaskEnt taskEnt = new TaskEnt();
        taskEnt.setrId(rId);
        TaskModel taskModel = TaskModel.valueOf(taskEnt);
        taskEnt.setTaskModel(taskModel);
        cache.writeBack(taskEnt.getId(), taskEnt);
    }

    public void initTaskResource() {
        //获取地图资源静态路径
        String path = ResourceUtil.getResourcePath(TaskResource.class);
        //加载资源
        logger.debug("开始加载任务静态资源");
        List<TaskResource> list = null;
        try {
            list = ExcelUtil.analysisWithRelativePath(path, TaskResource.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("加载任务静态资源错误:{}", e.getLocalizedMessage());
        }
        for (TaskResource taskResource : list) {
            taskResource.init();
            taskResourceMap.put(taskResource.getTaskId(), taskResource);
        }
        logger.debug("加载任务静态资源完成");
    }

    public TaskResource getTaskResource(int taskId) {
        return taskResourceMap.get(taskId);
    }
}
