package com.aiwan.server.user.role.task.process;

import com.aiwan.server.user.role.task.event.TaskParam;
import com.aiwan.server.user.role.task.resource.CompleteCondition;
import com.aiwan.server.user.role.task.service.TaskManager;
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
     *
     * @return 若为true，需保存实体到持久层
     */
    public boolean refreshExecute(TaskParam taskParam) {

        /*
        1.遍历所有进度（有静态资源中取出，相当于当前任务）
        2.判断任务进度中是否有于触发事假相同的类型
        3.若有，获取更新值
        4.改变更新值
         */
        boolean change = false;
        return change;
    }

    /**
     * 是否是相同类型且须修改
     */
    public abstract boolean isSameType(TaskParam taskParam, CompleteCondition completeCondition);

    /**
     * 修改当前任务进度
     */
    public abstract boolean modifyProgress();
}
