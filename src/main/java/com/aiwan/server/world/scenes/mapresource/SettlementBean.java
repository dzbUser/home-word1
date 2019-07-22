package com.aiwan.server.world.scenes.mapresource;

import com.aiwan.server.monster.resource.DropBean;
import com.aiwan.server.reward.model.RewardBean;

import java.util.List;

/**
 * 结算资源类
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
public class SettlementBean {


    /**
     * 奖励
     */
    private RewardBean rewardBean;

    public void doParse(String message) {
        this.rewardBean = new RewardBean();
        rewardBean.doParse(message);
    }


    public List<DropBean> getDropBeanList() {
        return rewardBean.getDropBeanList();
    }


    public int getExperience() {
        return rewardBean.getExperience();
    }

    public RewardBean getRewardBean() {
        return rewardBean;
    }

    public void setRewardBean(RewardBean rewardBean) {
        this.rewardBean = rewardBean;
    }
}
