package com.aiwan.server.user.role.task.facade;

import com.aiwan.server.base.event.anno.ReceiverAnno;
import com.aiwan.server.base.event.event.impl.DungeonClearanceEvent;
import com.aiwan.server.base.event.event.impl.EquipChangeEvent;
import com.aiwan.server.base.event.event.impl.RoleUpgradeEvent;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.base.event.event.impl.MonsterKillEvent;
import com.aiwan.server.user.role.task.event.AbstractTaskParam;
import com.aiwan.server.user.role.task.event.TaskParam;
import com.aiwan.server.user.role.task.process.AbstractProcessor;
import com.aiwan.server.user.role.task.process.TaskProgressType;
import com.aiwan.server.user.role.task.protocol.CM_CompleteTask;
import com.aiwan.server.user.role.task.protocol.CM_ReceiveTask;
import com.aiwan.server.user.role.task.protocol.CM_ViewCanReceiveTask;
import com.aiwan.server.user.role.task.protocol.CM_ViewProcessingTask;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * 任务模块门面
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
@Controller
public class TaskFacade {

    Logger logger = LoggerFactory.getLogger(TaskFacade.class);

    /**
     * 接收杀怪事件
     *
     * @param monsterKillEvent 杀怪事件
     */
    @ReceiverAnno
    public void killMonster(MonsterKillEvent monsterKillEvent) {
        //创建参数
        AbstractTaskParam taskParam = TaskProgressType.KILL_APPOINT_MONSTER.getParam(monsterKillEvent);
        //抛出杀怪事件
        AbstractProcessor abstractProcessor = AbstractProcessor.getProcessor(TaskProgressType.KILL_APPOINT_MONSTER);
        abstractProcessor.refreshExecute(taskParam);
    }

    /**
     * 接收任务升级事件
     *
     * @param roleUpgradeEvent 任务升级事件
     */
    @ReceiverAnno
    public void roleUpgrade(RoleUpgradeEvent roleUpgradeEvent) {
        //创建参数
        AbstractTaskParam taskParam = TaskProgressType.LEVEL_TYPE.getParam(roleUpgradeEvent);
        //抛出杀怪事件
        AbstractProcessor abstractProcessor = AbstractProcessor.getProcessor(TaskProgressType.LEVEL_TYPE);
        abstractProcessor.refreshExecute(taskParam);
    }

    /**
     * 接收角色装备栏变化
     *
     * @param equipChangeEvent 装备穿卸事件
     */
    @ReceiverAnno
    public void equipChange(EquipChangeEvent equipChangeEvent) {
        //创建参数
        AbstractTaskParam taskParam = TaskProgressType.EQUIP_NUM.getParam(equipChangeEvent);
        //抛出杀怪事件
        AbstractProcessor abstractProcessor = AbstractProcessor.getProcessor(TaskProgressType.EQUIP_NUM);
        abstractProcessor.refreshExecute(taskParam);
    }

    /**
     * 接收角色通关副本事件
     *
     * @param dungeonClearanceEvent 任务升级事件
     */
    @ReceiverAnno
    public void dungeonClearance(DungeonClearanceEvent dungeonClearanceEvent) {
        //创建参数
        AbstractTaskParam taskParam = TaskProgressType.DUNGEON_CLEARANCE.getParam(dungeonClearanceEvent);
        //抛出杀怪事件
        AbstractProcessor abstractProcessor = AbstractProcessor.getProcessor(TaskProgressType.DUNGEON_CLEARANCE);
        abstractProcessor.refreshExecute(taskParam);
    }


    public void viewCanReceiveTask(CM_ViewCanReceiveTask cm_viewCanReceiveTask, Session session) {
        if (session.getrId() == null) {
            logger.debug("错误包");
            return;
        }
        GetBean.getTaskService().viewCanReceiveTask(session.getrId());
    }

    /**
     * 接受任务
     */
    public void receiveTask(CM_ReceiveTask cm_receiveTask, Session session) {
        if (session.getrId() == null) {
            logger.debug("错误包");
            return;
        }
        GetBean.getTaskService().receiveTask(cm_receiveTask.getTaskId(), session.getrId());
    }

    /**
     * 查看进行中的任务
     */
    public void viewProcessingTask(CM_ViewProcessingTask cm_viewProcessingTask, Session session) {
        if (session.getrId() == null) {
            logger.debug("错误包");
            return;
        }
        GetBean.getTaskService().viewProcessingTask(session.getrId());
    }

    /**
     * 查看进行中的任务
     */
    public void completeTask(CM_CompleteTask cm_completeTask, Session session) {
        if (session.getrId() == null) {
            logger.debug("错误包");
            return;
        }
        GetBean.getTaskService().completeTask(session.getrId(), cm_completeTask.getTaskId());
    }
}
