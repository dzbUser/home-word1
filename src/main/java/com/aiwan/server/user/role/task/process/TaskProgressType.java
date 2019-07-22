package com.aiwan.server.user.role.task.process;

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
    LEVEL_TYPE(1),

    /**
     * 杀死指定怪物
     */
    KILL_APPOINT_MONSTER(2),

    /**
     * 穿指定位置装备
     */
    EQUIP_APPOINT_POSITION(3);

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
}
