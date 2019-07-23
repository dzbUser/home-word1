package com.aiwan.server.user.role.task.process.impl;

import com.aiwan.server.user.role.equipment.model.EquipmentModel;
import com.aiwan.server.user.role.task.entity.TaskElement;
import com.aiwan.server.user.role.task.entity.TaskProgressElement;
import com.aiwan.server.user.role.task.event.TaskParam;
import com.aiwan.server.user.role.task.process.AbstractProcessor;
import com.aiwan.server.user.role.task.process.TaskProgressType;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Component;

/**
 * 装备数量处理器
 */
@Component
public class EquipNumProcessor extends AbstractProcessor {
    @Override
    public TaskProgressType getEventType() {
        return TaskProgressType.EQUIP_NUM;
    }

    @Override
    public boolean isSameType(TaskParam taskParam, TaskProgressElement taskProgressElement) {
        if (taskParam.getTaskProgressType() != taskProgressElement.getTaskProgressType()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean modifyProgress(TaskParam taskParam, TaskProgressElement taskProgressElement, TaskElement taskElement) {
        EquipmentModel equipmentModel = GetBean.getEquipmentManager().load(taskParam.getRole().getId());
        taskProgressElement.setValue(equipmentModel.getEquipBarNum());
        if (taskProgressElement.getValue() >= taskProgressElement.getParam("value") && !taskProgressElement.isFinish()) {
            taskProgressElement.setFinish(true);
            taskElement.examineFinish();
        }
        return true;
    }

    @Override
    public void iniExcuteProgress(TaskProgressElement taskProgressElement, long rId) {
        EquipmentModel equipmentModel = GetBean.getEquipmentManager().load(rId);
        taskProgressElement.setValue(equipmentModel.getEquipBarNum());
        if (taskProgressElement.getValue() >= taskProgressElement.getParam("value") && !taskProgressElement.isFinish()) {
            taskProgressElement.setFinish(true);
        }
    }
}