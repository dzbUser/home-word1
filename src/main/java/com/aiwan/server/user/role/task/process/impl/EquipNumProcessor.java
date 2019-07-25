package com.aiwan.server.user.role.task.process.impl;

import com.aiwan.server.user.role.equipment.model.EquipmentModel;
import com.aiwan.server.user.role.task.entity.TaskElement;
import com.aiwan.server.user.role.task.entity.TaskProgressElement;
import com.aiwan.server.user.role.task.entity.impl.CommonProgress;
import com.aiwan.server.user.role.task.event.TaskParam;
import com.aiwan.server.user.role.task.event.impl.CommonParam;
import com.aiwan.server.user.role.task.process.AbstractProcessor;
import com.aiwan.server.user.role.task.process.TaskProgressType;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Component;

/**
 * 装备数量处理器
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
@Component
public class EquipNumProcessor extends AbstractProcessor<CommonParam, CommonProgress> {
    @Override
    public TaskProgressType getEventType() {
        return TaskProgressType.EQUIP_NUM;
    }

    @Override
    public boolean isSameType(CommonParam taskParam, CommonProgress taskProgressElement) {
        if (taskParam.getTaskProgressType() != taskProgressElement.getTaskProgressType()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean modifyProgress(CommonParam taskParam, CommonProgress taskProgressElement, TaskElement taskElement) {
        //获取装备栏
        EquipmentModel equipmentModel = GetBean.getEquipmentManager().load(taskParam.getRole().getId());
        //设置进度为当前装备的装备数
        taskProgressElement.setValue(equipmentModel.getEquipBarNum());
        if (taskProgressElement.getValue() >= taskProgressElement.getFinishValue() && !taskProgressElement.isFinish()) {
            taskProgressElement.setFinish(true);
            taskElement.examineFinish();
        }
        return true;
    }

    @Override
    public void initExcuteProgress(CommonProgress taskProgressElement, long rId) {
        //初始化任务为当前装备的装备数
        EquipmentModel equipmentModel = GetBean.getEquipmentManager().load(rId);
        taskProgressElement.setValue(equipmentModel.getEquipBarNum());
        if (taskProgressElement.getValue() >= taskProgressElement.getFinishValue() && !taskProgressElement.isFinish()) {
            taskProgressElement.setFinish(true);
        }
    }
}