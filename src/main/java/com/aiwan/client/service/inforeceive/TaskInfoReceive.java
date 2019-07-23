package com.aiwan.client.service.inforeceive;

import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.user.role.task.protocol.SM_ViewCanReceiveTask;
import com.aiwan.server.user.role.task.protocol.SM_ViewProcessingTask;
import com.aiwan.server.user.role.task.protocol.TaskElementMessage;
import com.aiwan.server.user.role.task.protocol.TaskProgessMessage;
import com.aiwan.server.user.role.task.resource.TaskResource;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;

import java.util.List;

/**
 * 任务模块任务信息接受类
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
@InfoReceiveObject
public class TaskInfoReceive {

    @InfoReceiveMethod(status = StatusCode.VIEW_CANRECEIVER_TASK)
    public void viewCanReceiveTask(SM_ViewCanReceiveTask sm_viewCanReceiveTask) {
        StringBuffer stringBuffer = new StringBuffer();
        List<Integer> list = sm_viewCanReceiveTask.getList();
        for (int i = 0; i < list.size(); i++) {
            TaskResource taskResource = GetBean.getTaskManager().getTaskResource(list.get(i));
            stringBuffer.append("[" + taskResource.getTaskId() + "]" + taskResource.getDec() + "\n");
        }
        stringBuffer.append("\n");
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage(stringBuffer.toString());
    }

    @InfoReceiveMethod(status = StatusCode.VIEW_PROCESSING_TASK)
    public void viewProcessingTask(SM_ViewProcessingTask sm_viewProcessingTask) {
        StringBuffer stringBuffer = new StringBuffer();
        List<TaskElementMessage> list = sm_viewProcessingTask.getList();
        for (TaskElementMessage taskElementMessage : list) {
            TaskResource taskResource = GetBean.getTaskManager().getTaskResource(taskElementMessage.getTaskId());
            stringBuffer.append("[" + taskResource.getTaskId() + "]" + taskResource.getDec());
            for (TaskProgessMessage taskProgessMessage : taskElementMessage.getList()) {
                stringBuffer.append(" " + taskProgessMessage.getValue() + "/" + taskProgessMessage.getFinishValue());
            }
            stringBuffer.append("\n");
        }
        stringBuffer.append("\n");
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage(stringBuffer.toString());
    }
}
