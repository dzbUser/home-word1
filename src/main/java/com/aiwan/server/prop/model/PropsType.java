package com.aiwan.server.prop.model;

import com.aiwan.server.prop.model.impl.BuffProps;
import com.aiwan.server.prop.model.impl.Equipment;
import com.aiwan.server.prop.model.impl.Experience;
import com.aiwan.server.prop.model.impl.MountDan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 道具类型
 */
public enum PropsType {

    /**
     * 经验丹
     */
    EXPERIENCE(1, Experience.class),

    /**
     * 坐骑升阶丹
     */
    MOUNTDAN(2, MountDan.class),

    /**
     * 装备类型
     */
    EQUIPMENT(3, Equipment.class),

    /**
     * buff道具
     */
    BUFFPROPS(4, BuffProps.class);

    /**
     * 空位置id
     */
    public final static int emptyId = 0;

    /**
     * 获取对应的道具类型
     */
    public static PropsType getType(int type) {
        for (PropsType propsType : values()) {
            if (propsType.getType() == type) {
                return propsType;
            }
        }
        throw new RuntimeException("道具类型获取失败");
    }

    static Logger logger = LoggerFactory.getLogger(PropsType.class);

    /**
     * 道具类型
     */
    private int type;

    /**
     * 道具类
     */
    private Class<? extends AbstractProps> clazz;

    PropsType(int type, Class<? extends AbstractProps> clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    /**
     * 创建道具
     */
    public AbstractProps createProp() {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            logger.error("道具类型生成实例错误", e);
            throw new IllegalArgumentException("生成道具实力[" + clazz.getName() + "]错误");
        }
    }


    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public Class<? extends AbstractProps> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends AbstractProps> clazz) {
        this.clazz = clazz;
    }


}
