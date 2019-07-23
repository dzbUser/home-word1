package com.aiwan.server.user.role.task.process.impl;

import com.aiwan.server.user.role.task.entity.TaskElement;
import com.aiwan.server.user.role.task.entity.TaskProgressElement;
import com.aiwan.server.user.role.task.event.TaskParam;
import com.aiwan.server.user.role.task.process.AbstractProcessor;
import com.aiwan.server.user.role.task.process.TaskProgressType;
import org.springframework.stereotype.Component;

/**
 * 副本通关任务处理器
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
@Component
public class DungeonClearanceProcessor extends AbstractProcessor {
    @Override
    public TaskProgressType getEventType() {
        return TaskProgressType.DUNGEON_CLEARANCE;
    }

    @Override
    public boolean isSameType(TaskParam taskParam, TaskProgressElement taskProgressElement) {
        if (taskParam.getTaskProgressType() != taskProgressElement.getTaskProgressType()) {
            return false;
        }
        //参数对比
        if (taskParam.getParam("mapId") == null || !taskParam.getParam("mapId").equals(taskProgressElement.getParam("mapId"))) {
            return false;
        }
        return true;
    }

    @Override
    public boolean modifyProgress(TaskParam taskParam, TaskProgressElement taskProgressElement, TaskElement taskElement) {
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