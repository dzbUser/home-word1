package com.aiwan.server.world.base.handler;

/**
 * 副本处理类
 */
public enum DungeonHandlerType {

    EXPERIENCE_HANDLER(1, ExperienceDungeonDungeonHandler.class) {},

    ;
    private int type;

    /**
     * buff效果类
     */
    private Class<? extends AbstractDungeonHandler> clzz;

    DungeonHandlerType(int type, Class<? extends AbstractDungeonHandler> clzz) {
        this.clzz = clzz;
        this.type = type;
    }

    public static DungeonHandlerType getHandler(int type) {
        for (DungeonHandlerType dungeonHandlerType : values()) {
            if (type == dungeonHandlerType.getType()) {
                return dungeonHandlerType;
            }
        }

        throw new IllegalArgumentException("找不到处理器,type:" + type);
    }

    /**
     * 获取对应的实例
     */
    public AbstractDungeonHandler creator() {
        try {
            return clzz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("生成副本处理器错误");
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
