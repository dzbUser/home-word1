package com.aiwan.server.user.role.buff.common;

import com.aiwan.server.base.executor.account.impl.AbstractAccountDelayCommand;
import com.aiwan.server.util.GetBean;

/**
 * buff时间到Command
 *
 * @author dengzebiao
 * @since 2019.7.5
 */
public class BuffOverCommand extends AbstractAccountDelayCommand {

    /**
     * 移除buffid
     */
    private int buffId;

    /**
     * 角色id
     */
    private Long rId;

    public BuffOverCommand(Long delay, String accountId, int buffId, long rId) {
        super(delay, accountId);
        this.buffId = buffId;
        this.rId = rId;
    }

    @Override
    public void active() {
        //移除buff
        GetBean.getBuffService().removeBuff(rId, buffId);
    }
}
