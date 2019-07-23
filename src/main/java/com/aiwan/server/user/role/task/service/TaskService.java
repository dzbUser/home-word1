package com.aiwan.server.user.role.task.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.task.entity.TaskElement;
import com.aiwan.server.user.role.task.entity.TaskProgressElement;
import com.aiwan.server.user.role.task.model.TaskModel;
import com.aiwan.server.user.role.task.protocol.SM_ViewCanReceiveTask;
import com.aiwan.server.user.role.task.protocol.SM_ViewProcessingTask;
import com.aiwan.server.user.role.task.protocol.TaskElementMessage;
import com.aiwan.server.user.role.task.protocol.TaskProgessMessage;
import com.aiwan.server.user.role.task.resource.TaskResource;
import com.aiwan.server.util.BaseConfiguration;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 任务业务类实现类
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
@Service
public class TaskService implements ITaskService {

    private final static Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskManager taskManager;

    @Override
    public void viewCanReceiveTask(long rId) {
        TaskModel taskModel = taskManager.load(rId);
        List<Integer> list = taskModel.getCanReceiveTask();
        SessionManager.sendMessageByRid(rId, StatusCode.VIEW_CANRECEIVER_TASK, SM_ViewCanReceiveTask.valueOf(list));
    }

    @Override
    public void receiveTask(int taskId, long rId) {
        TaskModel taskModel = taskManager.load(rId);
        if (taskModel.isFinished(taskId)) {
            logger.error("{}接收任务{}失败，任务已完成", rId, taskId);
            return;
        }
        if (taskModel.getTaskElement(taskId) != null) {
            logger.error("{}接收任务{}失败，任务已接收", rId, taskId);
            return;
        }
        //接收任务
        TaskResource taskResource = GetBean.getTaskManager().getTaskResource(taskId);
        if (taskResource == null) {
            logger.error("{}接收任务{}失败，静态资源为空", rId, taskId);
            return;
        }
        //开始创建任务
        taskModel.createTask(taskId, taskResource);
        //写回
        taskManager.save(taskModel);
        logger.error("{}接收任务{}成功", rId, taskId);
    }

    @Override
    public void viewProcessingTask(long rId) {
        TaskModel taskModel = taskManager.load(rId);
        Map<Integer, TaskElement> elements = taskModel.getTaskEnt().getTaskInfo().getTaskElementMap();
        List<TaskElementMessage> elementMessageList = new ArrayList<>();
        //遍历所有任务
        for (TaskElement taskElement : elements.values()) {
            List<TaskProgessMessage> progressMessages = new ArrayList<>();
            for (TaskProgressElement taskProgressElement : taskElement.getTaskProgress()) {
                progressMessages.add(TaskProgessMessage.valueOf(taskProgressElement.getValue(), taskProgressElement.getParamMap().get("value")));
            }
            elementMessageList.add(TaskElementMessage.valueOf(taskElement.getTaskId(), progressMessages));
        }
        SM_ViewProcessingTask sm_viewProcessingTask = SM_ViewProcessingTask.valueOf(elementMessageList);
        SessionManager.sendMessageByRid(rId, StatusCode.VIEW_PROCESSING_TASK, sm_viewProcessingTask);
    }

    @Override
    public void completeTask(long rId, int taskId) {
        TaskModel taskModel = taskManager.load(rId);
        TaskElement taskElement = taskModel.getTaskElement(taskId);
        if (taskElement == null) {
            logger.error("{}完成任务{}失败,没有接收该任务", rId, taskId);
            return;
        }
        if (!taskElement.isCanFinish()) {
            logger.error("{}完成任务{}失败,该任务不具备完成的条件", rId, taskId);
            return;
        }
        taskModel.completeTask(taskId);
        taskManager.save(taskModel);
        logger.info("{}完成任务{}成功", rId, taskId);
    }

}
