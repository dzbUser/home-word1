package com.aiwan.server.user.role.buff.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.publicsystem.annotation.Resource;

/**
 * buff效果资源
 */
@Resource("staticresource/effectResource.xls")
public class EffectResource {

    @CellMapping(name = "id")
    private int id;

    /**
     * 效果值
     */
    @CellMapping(name = "value")
    private int value;

    /**
     * buff类型
     */
    @CellMapping(name = "type")
    private int type;

    /**
     * 是否是周期buff
     */
    @CellMapping(name = "isPeriod")
    private int isPeriod;

    /**
     * 周期
     */
    @CellMapping(name = "period")
    private Long period;

    /**
     * 持续时间
     */
    @CellMapping(name = "duration")
    private long duration;

    /**
     * 效果id
     */
    @CellMapping(name = "uniqueId")
    private int uniqueId;

    /**
     * 效果id
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsPeriod() {
        return isPeriod;
    }

    public void setIsPeriod(int isPeriod) {
        this.isPeriod = isPeriod;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }
}
