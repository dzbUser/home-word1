package com.aiwan.server.user.backpack.model;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.prop.model.PropsType;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.user.backpack.entity.BackpackEnt;
import com.aiwan.server.util.GetBean;

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
     * 道具添加
     */
    public boolean putProps(int pid) {
        //获取道具静态资源
        PropsResource propsResource = GetBean.getPropsManager().getPropsResource(pid);
        //是否背包中有该道具
        AbstractProps abstractProps = getBackpackItem(pid);
        AbstractProps[] array = backpackEnt.getBackpackInfo().getAbstractProps();
        if (abstractProps == null) {
            //背包中没有该道具
            for (int i = 0; i < array.length; i++) {
                if (array[i].getId() == PropsType.emptyId) {
                    abstractProps = PropsType.getType(propsResource.getType()).createProp();
                    abstractProps.init(pid);
                    array[i] = abstractProps;
                    return true;
                }
            }
        } else {
            //背包中有该道具，查看是否可以叠加
            if (propsResource.getOverlay() == 1) {
                //可以叠加
                abstractProps.setNum(abstractProps.getNum() + 1);
                return true;
            } else {
                //不可叠加
                for (int i = 0; i < array.length; i++) {
                    if (array[i].getId() == PropsType.emptyId) {
                        array[i] = PropsType.getType(propsResource.getType()).createProp();
                        array[i].init(GetBean.getPropsManager().getPropsResource(pid));
                        return true;
                    }
                }
            }
        }
        return false;
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
     * 删除道具
     */
    public void removeProps(int pid) {
        //获取背包
        AbstractProps[] array = backpackEnt.getBackpackInfo().getAbstractProps();

        for (int i = 0; i < array.length; i++) {
            if (array[i].getId() == pid) {
                array[i] = PropsType.EMPTY.createProp();
                return;
            }
        }
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
}
