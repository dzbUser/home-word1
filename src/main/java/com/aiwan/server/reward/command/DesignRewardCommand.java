package com.aiwan.server.reward.command;

import com.aiwan.server.base.executor.account.impl.AbstractAccountCommand;
import com.aiwan.server.monster.resource.DropBean;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.reward.model.RewardBean;
import com.aiwan.server.util.GetBean;

import java.util.List;

/**
 * 指定奖励
 *
 * @author dengzebiao
 * @since 2019.7.24
 */
public class DesignRewardCommand extends AbstractAccountCommand {

    /**
     * 角色id
     */
    private Long rId;

    /**
     * 奖励
     */
    private RewardBean rewardBean;

    public DesignRewardCommand(String accountId, long rId, RewardBean rewardBean) {
        super(accountId);
        this.rId = rId;
        this.rewardBean = rewardBean;
    }

    @Override
    public void active() {
        //添加经验
        GetBean.getRoleService().addExperience(rId, rewardBean.getExperience());
        if (rewardBean == null || rewardBean.getDropBeanList().size() == 0) {
            //没有掉落物
            return;
        }
        //获取drop随机
        List<DropBean> list = rewardBean.getDropBeanList();
        for (DropBean dropBean : list) {
            //添加物品
            Session session = SessionManager.getSessionByAccountId(getAccountId());
            GetBean.getBackpackService().addPropToBack(getAccountId(), dropBean.getPropId(), dropBean.getNum(), session);
        }
    }

    @Override
    public String getTaskName() {
        return "DesignRewardCommand";
    }
}