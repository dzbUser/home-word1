package com.aiwan.server.user.role.task.process.impl;

import com.aiwan.server.user.role.player.model.Role;
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
 * 升级类型任务处理器
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
@Component
public class LevelUpgradeProcessor extends AbstractProcessor<CommonParam, CommonProgress> {
    @Override
    public TaskProgressType getEventType() {
        return TaskProgressType.LEVEL_TYPE;
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
        taskProgressElement.setValue(taskParam.getRole().getLevel());
        if (taskProgressElement.getValue() >= taskProgressElement.getFinishValue() && !taskProgressElement.isFinish()) {
            taskProgressElement.setFinish(true);
            taskElement.examineFinish();
        }
        return true;
    }

    @Override
    public void iniExcuteProgress(CommonProgress taskProgressElement, long rId) {
        Role role = GetBean.getRoleManager().load(rId);
        taskProgressElement.setValue(role.getLevel());
        if (taskProgressElement.getValue() >= taskProgressElement.getFinishValue() && !taskProgressElement.isFinish()) {
            taskProgressElement.setFinish(true);
        }
    }
}
