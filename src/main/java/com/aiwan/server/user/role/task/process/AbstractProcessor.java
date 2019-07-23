package com.aiwan.server.user.role.task.process;

import com.aiwan.server.user.role.task.entity.TaskElement;
import com.aiwan.server.user.role.task.entity.TaskInfo;
import com.aiwan.server.user.role.task.entity.TaskProgressElement;
import com.aiwan.server.user.role.task.event.TaskParam;
import com.aiwan.server.user.role.task.model.TaskModel;
import com.aiwan.server.user.role.task.service.TaskManager;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 任务事件处理器 抽象接口
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public abstract class AbstractProcessor {

    Logger logger = LoggerFactory.getLogger(AbstractProcessor.class);

    @Autowired
    private TaskManager taskManager;

    /**
     * 存放任务处理器的集合
     */
    private static final Map<TaskProgressType, AbstractProcessor> processorMap = new HashMap<>();

    /**
     * 获取处理器
     *
     * @param taskProgressType 事件类型
     * @return
     */
    public static AbstractProcessor getProcessor(TaskProgressType taskProgressType) {
        return processorMap.get(taskProgressType);
    }

    /**
     * 初始化存入处理器类型
     */
    @PostConstruct
    private final void init() {
        processorMap.put(getEventType(), this);
    }

    public abstract TaskProgressType getEventType();

    /**
     * 更新完成进度
     */
    public void refreshExecute(TaskParam taskParam) {
        logger.debug("{}触发任务更新", taskParam.getRole().getId());
        boolean change = false;
        //获取任务
        TaskModel taskModel = GetBean.getTaskManager().load(taskParam.getRole().getId());
        TaskInfo taskInfo = taskModel.getTaskEnt().getTaskInfo();
        //遍历所有任务
        for (TaskElement taskElement : taskInfo.getTaskElementMap().values()) {
            //遍历所有任务进度
            for (TaskProgressElement taskProgressElement : taskElement.getTaskProgress()) {
                //判断任务进度的类型是否一样
                if (isSameType(taskParam, taskProgressElement)) {
                    change = change || modifyProgress(taskParam, taskProgressElement, taskElement);
                }
            }
        }
        if (change) {
            //发生变化
            GetBean.getTaskManager().save(taskModel);
        }
    }

    /**
     * 是否是相同类型且须修改
     */
    public abstract boolean isSameType(TaskParam taskParam, TaskProgressElement taskProgressElement);

    /**
     * 修改当前任务进度
     */
    public abstract boolean modifyProgress(TaskParam taskParam, TaskProgressElement taskProgressElement, TaskElement taskElement);

    /**
     * 初始化任务进度
     */
    public abstract void iniExcuteProgress(TaskProgressElement taskProgressElement, long rId);
}
