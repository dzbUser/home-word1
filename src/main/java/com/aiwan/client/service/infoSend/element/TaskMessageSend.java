package com.aiwan.client.service.infoSend.element;

import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.client.util.Verification;
import com.aiwan.server.user.role.task.protocol.CM_CompleteTask;
import com.aiwan.server.user.role.task.protocol.CM_ReceiveTask;
import com.aiwan.server.user.role.task.protocol.CM_ViewCanReceiveTask;
import com.aiwan.server.user.role.task.protocol.CM_ViewProcessingTask;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;

/**
 * 任务协议发送
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public enum TaskMessageSend {

    VIEW_CANRECEIVE_TASK(0) {
        @Override
        public void messageSend(String message) {
            CM_ViewCanReceiveTask cm_viewCanReceiveTask = new CM_ViewCanReceiveTask();
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.VIEW_CANRECEIVE_TASK, cm_viewCanReceiveTask));
        }
    },
    RECEIVE_TASK(1) {
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)) {
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }
            int taskId = Integer.parseInt(message);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.RECEIVE_TASK, CM_ReceiveTask.valueOf(taskId)));
        }

        @Override
        public boolean verify(String message) {
            if (!Verification.canParseNum(message)) {
                return false;
            }
            return true;
        }
    },
    VIEW_PROCESSING_TASK(2) {
        @Override
        public void messageSend(String message) {
            CM_ViewProcessingTask cm_viewProcessingTask = new CM_ViewProcessingTask();
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.VIEW_PROCESSING_TASK, cm_viewProcessingTask));
        }
    },
    COMPLETE_TASK(3) {
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)) {
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }
            int taskId = Integer.parseInt(message);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.COMPLETE_TASK, CM_CompleteTask.valueOf(taskId)));
        }

        @Override
        public boolean verify(String message) {
            if (!Verification.canParseNum(message)) {
                return false;
            }
            return true;
        }
    },

    ;

    TaskMessageSend(int num) {
        this.num = num;
    }

    /**
     * 所属位置
     */
    private int num;

    /**
     * 发送协议
     */
    public void messageSend(String message) {

    }

    /**
     * 获取对应发送类
     */
    public static TaskMessageSend getMessageSend(int num) {
        for (TaskMessageSend messageSend : values()) {
            if (messageSend.getNum() == num) {
                return messageSend;
            }
        }
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage("没有此功能选项");
        return null;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    /**
     * 验证指令是否符合格式
     */
    public boolean verify(String message) {
        return true;
    }
}
