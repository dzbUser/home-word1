package com.aiwan.server.world.scenes.mapresource;

import com.aiwan.server.monster.resource.DropBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 结算资源类
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
public class SettlementBean {

    /**
     * 掉落列表
     */
    private List<DropBean> dropBeanList = new ArrayList<>();

    /**
     * 经验值
     */
    private int experience;

    public void doParse(String message) {
        String[] unit = message.split(" ");
        if (!unit[0].equals("#")) {
            String[] dropUnits = unit[0].split("/");
            for (String dropUnit : dropUnits) {
                String[] drop = dropUnit.split("=");
                DropBean dropBean = DropBean.valueOf(Integer.parseInt(drop[0]), Integer.parseInt(drop[1]));
                this.dropBeanList.add(dropBean);
            }
        }
        this.experience = Integer.parseInt(unit[1]);
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
