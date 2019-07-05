package com.aiwan.server.user.role.buff.entity;

import com.aiwan.server.user.role.buff.resource.BuffResource;
import com.aiwan.server.util.GetBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.concurrent.ScheduledFuture;

/**
 * buff入库单元
 *
 * @author dengzebiao
 * @since 2019.7.5
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
public class BuffElement {

    /**
     * buff id
     */
    private int buffId;

    /**
     * 结束时间戳
     */
    private long overTime;


    public int getBuffId() {
        return buffId;
    }

    public void setBuffId(int buffId) {
        this.buffId = buffId;
    }

    public long getOverTime() {
        return overTime;
    }

    public void setOverTime(long overTime) {
        this.overTime = overTime;
    }


    @JsonIgnore
    public static BuffElement valueOf(int buffId, long overTime) {
        BuffElement buffElement = new BuffElement();
        buffElement.setBuffId(buffId);
        buffElement.setOverTime(overTime);
        return buffElement;
    }

    @JsonIgnore
    public BuffResource getBuffResource() {
        return GetBean.getBuffManager().getBuffResourceByBuffId(buffId);
    }

}
