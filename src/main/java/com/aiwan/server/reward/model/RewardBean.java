package com.aiwan.server.reward.model;

import com.aiwan.server.monster.resource.DropBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 奖励类
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public class RewardBean {

    /**
     * 掉落物
     */
    private List<DropBean> dropBeanList = new ArrayList<>();

    /**
     * 经验值
     */
    private int experience;

    public static RewardBean valueOf(List<DropBean> dropBeanList, int experience) {
        RewardBean rewardBean = new RewardBean();
        rewardBean.setExperience(experience);
        rewardBean.setDropBeanList(dropBeanList);
        return rewardBean;
    }

    public void doParse(String message) {
        String[] unit = message.split(":");
        if (!unit[0].equals("#")) {
            String[] dropUnits = unit[0].split("/");
            for (String dropUnit : dropUnits) {
                String[] drop = dropUnit.split("=");
                DropBean dropBean = DropBean.valueOf(Integer.parseInt(drop[0]), Integer.parseInt(drop[1]));
                this.getDropBeanList().add(dropBean);
            }
        }
        this.setExperience(Integer.parseInt(unit[1]));
    }

    public List<DropBean> getDropBeanList() {
        return dropBeanList;
    }

    public void setDropBeanList(List<DropBean> dropBeanList) {
        this.dropBeanList = dropBeanList;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
