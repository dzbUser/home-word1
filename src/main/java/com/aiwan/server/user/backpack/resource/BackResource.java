package com.aiwan.server.user.backpack.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;

/**
 * @author dengzebiao
 * @since 2019.6.24
 * 背包静态资源
 */
public class BackResource {

    /**
     * 背包最大数量
     */
    @CellMapping(name = "maxNum")
    private int maxNum;

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }
}
