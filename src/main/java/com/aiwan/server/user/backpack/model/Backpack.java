package com.aiwan.server.user.backpack.model;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.prop.model.PropsType;
import com.aiwan.server.prop.model.impl.MountDan;
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
     * 是否为空
     */
    public boolean isEmpty() {
        //获取背包
        AbstractProps[] array = backpackEnt.getBackpackInfo().getAbstractProps();

        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                return false;
            }
        }
        return true;
    }


    /**
     * 获取对应位置的道具
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
        //查看是否可以叠加数量为num的该道具到背包
        //道具上限
        int limit = abstractProps.getPropsResource().getLimit();
        AbstractProps[] array = backpackEnt.getBackpackInfo().getAbstractProps();

        for (int i = 0; i < array.length; i++) {
            //是否叠加到现存背包项
            if (array[i] != null && array[i].getResourceId() == abstractProps.getResourceId()) {
                int allNum = array[i].getNum() + num;
                if (allNum > limit) {
                    //总数大于上限
                    array[i].setNum(limit);
                    num = allNum - limit;
                } else {
                    array[i].setNum(allNum);
                    return true;
                }
            }
        }
        //获取背包
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null && num > 0) {
                array[i] = PropsType.getType(abstractProps.getPropsResource().getType()).createProp();
                array[i].init(abstractProps.getPropsResource());
                if (num > limit) {
                    //大于上限
                    array[i].setNum(limit);
                    num = num - limit;
                } else {
                    array[i].setNum(num);
                    //以存完
                    return true;
                }
            }
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
            if (array[i] == null) {
                array[i] = abstractProps;
                return true;
            }
        }
        return false;
    }


    /**
     * 按照唯一id去除道具
     */
    public boolean deductionPropByObjectId(Long objectId, int num) {
        //获取背包
        AbstractProps[] array = backpackEnt.getBackpackInfo().getAbstractProps();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].getObjectId().equals(objectId) && array[i].getNum() >= num) {
                if (array[i].getNum() > num) {
                    array[i].setNum(array[i].getNum() - num);
                    return true;
                }
                array[i] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * 是否可以添加数量为num的该道具到背包
     */
    public boolean isCanSavePropsInNum(int resourceId, int num) {
        //获取该道具资源
        PropsResource propsResource = GetBean.getPropsManager().getPropsResource(resourceId);
        //该物品的上限
        int limit = propsResource.getLimit();
        //可以存的数量
        int allNum = 0;
        //获取背包道具
        AbstractProps[] array = getBackpackInfo().getAbstractProps();

        for (int i = 0; i < array.length; i++) {
            //叠加可存总数
            if (array[i] != null && array[i].getResourceId() == resourceId) {
                allNum += limit - array[i].getNum();
            } else if (array[i] == null) {
                allNum += limit;
            }
            if (allNum >= num) {
                return true;
            }
        }
        return false;

    }

    /**
     * 查看背包中该资源id的数量
     */
    public int getNumOfResourceId(int resourceId) {
        //获取背包栏
        AbstractProps[] array = backpackEnt.getBackpackInfo().getAbstractProps();
        int num = 0;
        for (int i = 0; i < array.length; i++) {
            //获取道具数
            if (array[i] != null && array[i].getResourceId() == resourceId) {
                num += array[i].getNum();
            }
        }
        return num;
    }

    /**
     * 按照资源id扣除道具
     */
    public boolean deductionByResourceIdInNum(int resourceId, int num) {
        //检查数量是否足够
        int haveNum = getNumOfResourceId(resourceId);
        if (haveNum < num) {
            return false;
        }
        //获取背包栏
        AbstractProps[] array = backpackEnt.getBackpackInfo().getAbstractProps();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].getResourceId() == resourceId) {
                int surplusNum = num - array[i].getNum();
                if (surplusNum > 0) {
                    //不够扣除
                    num = surplusNum;
                    array[i] = null;
                } else {
                    if (surplusNum == 0) {
                        //扣完
                        array[i] = null;
                    } else {
                        array[i].setNum(array[i].getNum() - num);
                    }
                    return true;
                }
            }
        }
        return true;
    }


}
