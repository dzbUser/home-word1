package com.aiwan.server.user.role.buff.protocol;

import java.io.Serializable;

/**
 * buff信息
 *
 * @author dengzebiao
 * @since 2019.7.5
 */
public class BuffMessage implements Serializable {
    /**
     * buff id
     */
    private int buffId;

    /**
     * 结束时间
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

    public static BuffMessage valueOf(int buffId, long overTime) {
        BuffMessage buffMessage = new BuffMessage();
        buffMessage.setBuffId(buffId);
        buffMessage.setOverTime(overTime);
        return buffMessage;
    }
}
