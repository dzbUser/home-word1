package com.aiwan.server.user.backpack.model;

import javax.persistence.Column;

/**
 * @author dengzebiao
 * @since 2019.6.4
 * 背包内道具
 * */
public class BackpackItem {

    /** 道具id */
    private int id;

    /** 数量 */
    private int num;

    /** 是否具有时限 */
    private boolean limit;

    /** 若有时限，到期时间 */
    private Long deadline;

    public int getId() {
        return id;
    }

    public BackpackItem setId(int id) {
        this.id = id;
        return this;
    }

    public int getNum() {
        return num;
    }

    public BackpackItem setNum(int num) {
        this.num = num;
        return this;
    }

    public boolean isLimit() {
        return limit;
    }

    public BackpackItem setLimit(boolean limit) {
        this.limit = limit;
        return this;
    }

    public Long getDeadline() {
        return deadline;
    }

    public BackpackItem setDeadline(Long deadline) {
        this.deadline = deadline;
        return this;
    }
}
