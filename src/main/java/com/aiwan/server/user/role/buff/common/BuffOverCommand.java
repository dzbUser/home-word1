package com.aiwan.server.user.role.buff.common;

import com.aiwan.server.base.executor.account.impl.AbstractAccountDelayCommand;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * buff时间到Command
 *
 * @author dengzebiao
 * @since 2019.7.5
 */
public class BuffOverCommand extends AbstractAccountDelayCommand {
    Logger logger = LoggerFactory.getLogger(AbstractAccountDelayCommand.class);

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
        setTaskName("buff时间到Command");
    }

    @Override
    public void active() {
        logger.info("buffId:{},移除buff所属角色:{},触发定时器，开始移除buff", buffId, rId);
        //移除buff
        GetBean.getBuffService().removeBuff(rId, buffId);

    }
}
