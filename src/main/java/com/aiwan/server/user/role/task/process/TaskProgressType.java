package com.aiwan.server.user.role.task.process;

import com.aiwan.server.base.event.event.IEvent;
import com.aiwan.server.base.event.event.impl.DungeonClearanceEvent;
import com.aiwan.server.base.event.event.impl.EquipChangeEvent;
import com.aiwan.server.base.event.event.impl.RoleUpgradeEvent;
import com.aiwan.server.user.role.equipment.model.EquipmentModel;
import com.aiwan.server.base.event.event.impl.MonsterKillEvent;
import com.aiwan.server.user.role.task.event.AbstractTaskParam;
import com.aiwan.server.user.role.task.event.TaskParam;
import com.aiwan.server.user.role.task.event.impl.CommonParam;
import com.aiwan.server.user.role.task.event.impl.DungeonClearanceParam;
import com.aiwan.server.user.role.task.event.impl.KillAppointMonsterParam;
import com.aiwan.server.util.GetBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务事件类型枚举类
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public enum TaskProgressType {

    /**
     * 等级类型
     */
    LEVEL_TYPE(1) {
        @Override
        public Map<String, Integer> shiftParam(String param) {
            Map<String, Integer> paramMap = new HashMap<>();
            paramMap.put("value", Integer.parseInt(param));
            return paramMap;
        }

        @Override
        public int getOriginValue(long rId) {
            return GetBean.getRoleManager().load(rId).getLevel();
        }

        @Override
        public AbstractTaskParam getParam(IEvent event) {
            RoleUpgradeEvent roleUpgradeEvent = (RoleUpgradeEvent) event;
            CommonParam taskParam = new CommonParam(roleUpgradeEvent.getRole(), this);
            return taskParam;
        }
    },

    /**
     * 杀死指定怪物
     */
    KILL_APPOINT_MONSTER(2) {
        @Override
        public Map<String, Integer> shiftParam(String param) {
            Map<String, Integer> paramMap = new HashMap<>();
            String[] keyValue = param.split("=");
            if (keyValue.length < 2) {
                throw new IllegalArgumentException("KILL_APPOINT_MONSTER 参数转换错误");
            }
            paramMap.put("monsterId", Integer.parseInt(keyValue[0]));
            paramMap.put("value", Integer.parseInt(keyValue[1]));
            return paramMap;
        }

        @Override
        public AbstractTaskParam getParam(IEvent event) {
            MonsterKillEvent monsterKillEvent = (MonsterKillEvent) event;
            KillAppointMonsterParam killAppointMonsterParam = new KillAppointMonsterParam(monsterKillEvent.getRole(), this, monsterKillEvent.getMonsterId());
            return killAppointMonsterParam;
        }
    },

    /**
     * 穿指定位置装备
     */
    EQUIP_NUM(3) {
        @Override
        public Map<String, Integer> shiftParam(String param) {
            Map<String, Integer> paramMap = new HashMap<>();
            paramMap.put("value", Integer.parseInt(param));
            return paramMap;
        }

        @Override
        public int getOriginValue(long rId) {
            EquipmentModel equipmentModel = GetBean.getEquipmentManager().load(rId);
            return equipmentModel.getEquipBarNum();
        }

        @Override
        public AbstractTaskParam getParam(IEvent event) {
            EquipChangeEvent equipChangeEvent = (EquipChangeEvent) event;
            CommonParam taskParam = new CommonParam(equipChangeEvent.getRole(), this);
            return taskParam;
        }
    },
    /**
     * 任务通关类型
     */
    DUNGEON_CLEARANCE(4) {
        @Override
        public Map<String, Integer> shiftParam(String param) {
            Map<String, Integer> paramMap = new HashMap<>();
            String[] keyValue = param.split("=");
            if (keyValue.length < 2) {
                throw new IllegalArgumentException("DUNGEON_CLEARANCE 参数转换错误");
            }
            paramMap.put("mapId", Integer.parseInt(keyValue[0]));
            paramMap.put("value", Integer.parseInt(keyValue[1]));
            return paramMap;
        }

        @Override
        public AbstractTaskParam getParam(IEvent event) {
            DungeonClearanceEvent dungeonClearanceEvent = (DungeonClearanceEvent) event;
            DungeonClearanceParam param = new DungeonClearanceParam(dungeonClearanceEvent.getRole(), this, dungeonClearanceEvent.getMapId());
            return param;
        }
    },
    ;

    private int type;

    TaskProgressType(int type) {
        this.type = type;
    }

    public static TaskProgressType getType(int type) {
        for (TaskProgressType taskProgressType : values()) {
            if (taskProgressType.type == type) {
                return taskProgressType;
            }
        }
        throw new IllegalArgumentException("找不到对应类型为{}的任务事件类型");
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 转换参数为map
     *
     * @param param
     * @return
     */
    public Map<String, Integer> shiftParam(String param) {
        return null;
    }

    /**
     * 获取初始值
     *
     * @param rId 角色id
     * @return
     */
    public int getOriginValue(long rId) {
        return 0;
    }

    /**
     * 获取任务参数
     *
     * @param event 事件
     * @return
     */
    public AbstractTaskParam getParam(IEvent event) {
        return null;
    }
}
