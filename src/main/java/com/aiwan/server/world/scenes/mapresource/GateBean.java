package com.aiwan.server.world.scenes.mapresource;

import com.aiwan.server.monster.resource.DropBean;
import com.aiwan.server.reward.model.RewardBean;

import java.util.List;

/**
 * 关卡类
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
public class GateBean {

    /**
     * 关卡数
     */
    private int gateNum;

    /**
     * 怪物id
     */
    private int monsterId;

    /**
     * 怪物数量
     */
    private int monsterNum;

    /**
     * 奖励
     */
    private RewardBean rewardBean;

    public void doParse(String message) {
        String[] unit = message.split(":");
        this.gateNum = Integer.parseInt(unit[0]);
        this.monsterId = Integer.parseInt(unit[1]);
        this.monsterNum = Integer.parseInt(unit[2]);
        this.rewardBean = new RewardBean();
        rewardBean.setExperience(Integer.parseInt(unit[4]));
        if (!unit[3].equals("#")) {
            String[] dropUnits = unit[3].split("/");
            for (String dropUnit : dropUnits) {
                String[] drop = dropUnit.split("=");
                DropBean dropBean = DropBean.valueOf(Integer.parseInt(drop[0]), Integer.parseInt(drop[1]));
                rewardBean.getDropBeanList().add(dropBean);
            }
        }
    }

    public int getGateNum() {
        return gateNum;
    }

    public void setGateNum(int gateNum) {
        this.gateNum = gateNum;
    }

    public int getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(int monsterId) {
        this.monsterId = monsterId;
    }

    public int getMonsterNum() {
        return monsterNum;
    }

    public void setMonsterNum(int monsterNum) {
        this.monsterNum = monsterNum;
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
