package com.aiwan.server.user.backpack.model;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.prop.model.PropsType;
import com.aiwan.server.prop.model.impl.MountDan;
import com.aiwan.server.user.backpack.entity.BackpackEnt;

/**
 * @author dengzebiao
 * @since 2019.6.4
 * 背包业务使用类
 * */
public class Backpack {
    /** 背包实体类 */
    private BackpackEnt backpackEnt;


    /** 背包获取 */
    public BackpackInfo getBackpackInfo(){
        return backpackEnt.getBackpackInfo();
    }

    public String getAccountId() {
        return backpackEnt.getAccountId();
    }

    public void setAccountId(String accountId) {
        backpackEnt.setAccountId(accountId);
    }

    public Long getCreateTime() {
        return backpackEnt.getCreateTime();
    }

    public void setCreateTime(Long createTime) {
        backpackEnt.setCreateTime(createTime);
    }

    public Long getUpdateTime() {
        return backpackEnt.getUpdateTime();
    }

    public void setUpdateTime(Long updateTime) {
        backpackEnt.setUpdateTime(updateTime);
    }

    public int getMaxNum() {
        return backpackEnt.getMaxNum();
    }

    public void setMaxNum(int maxNum) {
        backpackEnt.setMaxNum(maxNum);
    }

    public void setBackpackEnt(BackpackInfo backpackItems) {
        backpackEnt.setBackpackInfo(backpackItems);
    }

    public BackpackEnt getBackpackEnt() {
        return backpackEnt;
    }

    public Backpack setBackpackEnt(BackpackEnt backpackEnt) {
        this.backpackEnt = backpackEnt;
        return this;
    }

    /**
     * 获取对应道具
     */
    public AbstractProps getBackpackItem(int id) {
        AbstractProps[] abstractProps = backpackEnt.getBackpackInfo().getAbstractProps();
        for (int i = 0; i < abstractProps.length; i++) {
            if (abstractProps[i] != null && abstractProps[i].getId() == id) {
                return abstractProps[i];
            }
        }
        return null;
    }


    /**
     * 是否为空
     */
    public boolean isEmpty() {
        //获取背包
        AbstractProps[] array = backpackEnt.getBackpackInfo().getAbstractProps();

        for (int i = 0; i < array.length; i++) {
            if (array[i].getId() != PropsType.emptyId) {
                return false;
            }
        }
        return true;
    }


    /**
     * 获取对应位置的装备
     */
    public AbstractProps getPropByPosition(int position) {
        if (position < getMaxNum()) {
            return backpackEnt.getBackpackInfo().getAbstractProps()[position];
        }
        return null;
    }

    /**
     * 添加可叠加道具
     */
    public boolean putOverlayProps(AbstractProps abstractProps, int num) {
        //道具上限
        int limit = abstractProps.getPropsResource().getLimit();
        AbstractProps[] array = backpackEnt.getBackpackInfo().getAbstractProps();

        for (int i = 0; i < array.length; i++) {
            //是否叠加到现存背包项
            if (array[i].getId() == abstractProps.getId()) {
                int allnum = array[i].getNum() + num;
                if (allnum > limit) {
                    //总数大于上限
                    array[i].setNum(limit);
                    num = allnum - limit;
                } else {
                    array[i].setNum(allnum);
                    return true;
                }
            }
        }
        //获取背包

        for (int i = 0; i < array.length; i++) {
            if (array[i].getId() == PropsType.emptyId && num > 0) {
                array[i] = PropsType.getType(abstractProps.getPropsResource().getType()).createProp();
                array[i].init(abstractProps.getPropsResource());
                if (num > limit) {
                    //大于上限
                    array[i].setNum(limit);
                    num = num - limit;
                } else {
                    array[i].setNum(num);
                    return true;
                }
            }
        }
        if (num > 0) {
            //不够存放
            return false;
        }
        return true;

    }

    /**
     * 添加不可叠加道具
     */
    public boolean putNoOverlayProps(AbstractProps abstractProps) {
        //获取背包
        AbstractProps[] array = backpackEnt.getBackpackInfo().getAbstractProps();
        for (int i = 0; i < array.length; i++) {
            if (array[i].getId() == PropsType.emptyId) {
                array[i] = abstractProps;
                return true;
            }
        }
        return false;
    }

    /**
     * 去除可叠加道具
     */
    public boolean deductionProp(AbstractProps abstractProps) {
        //获取背包
        AbstractProps[] array = backpackEnt.getBackpackInfo().getAbstractProps();
        //不可叠加
        for (int i = 0; i < array.length; i++) {
            if (array[i].getObjectId().equals(abstractProps.getObjectId())) {
                //是否可叠加
                if (abstractProps.getPropsResource().getOverlay() == 1) {
                    int num = array[i].getNum() - 1;
                    if (num == 0) {
                        //道具用完了
                        array[i] = PropsType.EMPTY.createProp();
                        array[i].init(PropsType.emptyId);
                    } else {
                        array[i].setNum(num);
                    }
                    return true;
                } else {
                    array[i] = PropsType.EMPTY.createProp();
                    return true;
                }
            }
        }
        return false;
    }

    /** 坐骑升阶 */
    public boolean mountUpgrade(){
        AbstractProps[] array = backpackEnt.getBackpackInfo().getAbstractProps();
        for (int i = 0; i < array.length; i++) {
            if (array[i].getId() == MountDan.id) {
                int num = array[i].getNum() - 1;
                if (num == 0) {
                    //升阶丹用完
                    array[i] = PropsType.EMPTY.createProp();
                    array[i].init(PropsType.emptyId);
                    return true;
                } else {
                    array[i].setNum(num);
                    return true;
                }
            }
        }
        return false;
    }
}
