package com.aiwan.server.user.role.task.process.impl;

import com.aiwan.server.base.event.event.impl.DungeonClearanceEvent;
import com.aiwan.server.user.role.task.entity.TaskElement;
import com.aiwan.server.user.role.task.entity.TaskProgressElement;
import com.aiwan.server.user.role.task.entity.impl.DungeonClearanceProgress;
import com.aiwan.server.user.role.task.event.AbstractTaskParam;
import com.aiwan.server.user.role.task.event.TaskParam;
import com.aiwan.server.user.role.task.event.impl.DungeonClearanceParam;
import com.aiwan.server.user.role.task.process.AbstractProcessor;
import com.aiwan.server.user.role.task.process.TaskProgressType;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

/**
 * 副本通关任务处理器
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
@Component
public class DungeonClearanceProcessor extends AbstractProcessor<DungeonClearanceParam, DungeonClearanceProgress> {

    @Override
    public TaskProgressType getEventType() {
        return TaskProgressType.DUNGEON_CLEARANCE;
    }

    @Override
    public boolean isSameType(DungeonClearanceParam taskParam, DungeonClearanceProgress taskProgressElement) {
        if (taskParam.getTaskProgressType() != taskProgressElement.getTaskProgressType()) {
            return false;
        }
        //参数对比
        if (taskParam.getMapId() != taskProgressElement.getMapId()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean modifyProgress(DungeonClearanceParam taskParam, DungeonClearanceProgress taskProgressElement, TaskElement taskElement) {
        //通关副本数+1
        taskProgressElement.setValue(taskProgressElement.getValue() + 1);
        if (taskProgressElement.getValue() >= taskProgressElement.getFinishValue() && !taskProgressElement.isFinish()) {
            //判断该进度是否完成
            taskProgressElement.setFinish(true);
            taskElement.examineFinish();
        }
        return true;
    }

    @Override
    public void initExcuteProgress(DungeonClearanceProgress taskProgressElement, long rId) {

    }
}