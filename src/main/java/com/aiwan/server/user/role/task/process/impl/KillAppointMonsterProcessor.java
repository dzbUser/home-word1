package com.aiwan.server.user.role.task.process.impl;

import com.aiwan.server.user.role.task.entity.TaskElement;
import com.aiwan.server.user.role.task.entity.TaskProgressElement;
import com.aiwan.server.user.role.task.event.TaskParam;
import com.aiwan.server.user.role.task.event.impl.KillAppointMonsterParam;
import com.aiwan.server.user.role.task.process.AbstractProcessor;
import com.aiwan.server.user.role.task.process.TaskProgressType;
import org.springframework.stereotype.Component;


/**
 * 击杀指定怪物处理器
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
@Component
public class KillAppointMonsterProcessor extends AbstractProcessor<KillAppointMonsterParam> {
    @Override
    public TaskProgressType getEventType() {
        return TaskProgressType.KILL_APPOINT_MONSTER;
    }

    @Override
    public boolean isSameType(KillAppointMonsterParam taskParam, TaskProgressElement taskProgressElement) {
        if (taskParam.getTaskProgressType() != taskProgressElement.getTaskProgressType()) {
            return false;
        }
        //参数对比
        if (taskParam.getMonsterId() != taskProgressElement.getParam("monsterId")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean modifyProgress(KillAppointMonsterParam taskParam, TaskProgressElement taskProgressElement, TaskElement taskElement) {
        taskProgressElement.setValue(taskProgressElement.getValue() + 1);
        if (taskProgressElement.getValue() >= taskProgressElement.getParam("value") && !taskProgressElement.isFinish()) {
            taskProgressElement.setFinish(true);
            taskElement.examineFinish();
        }
        return true;
    }

    @Override
    public void iniExcuteProgress(TaskProgressElement taskProgressElement, long rId) {

    }
}
