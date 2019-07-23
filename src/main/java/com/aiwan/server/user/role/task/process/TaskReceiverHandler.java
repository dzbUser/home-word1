package com.aiwan.server.user.role.task.process;

import com.aiwan.server.user.role.task.service.TaskManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 任务处理器
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public class TaskReceiverHandler {

    @Autowired
    private TaskManager taskManager;


//    @Override
//    public void excute(TaskEvent event) {
//        /*
//        1.获取对应玩家的任务对象
//        2.获取该事件类型的处理器
//        3.便利所有任务，调用处理器的更新执行
//        4.如果更新，保存
//         */
//        //获取任务对象
//        TaskInfo taskInfo = GetBean.getTaskManager().load(event.getRole().getId()).getTaskEnt().getTaskInfo();
//        AbstractProcessor abstractProcessor = AbstractProcessor.getProcessor(event.getTaskProgressType());
//        //遍历所有任务
//        for (TaskElement taskElement:taskInfo.getTaskElementMap().values()){
//            TaskResource taskResource = GetBean.getTaskManager().getTaskResource(taskElement.getTaskId());
//        }
//    }


}
