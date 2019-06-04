package com.aiwan.server.user.backpack.model;

import com.aiwan.server.user.backpack.entity.BackpackEnt;

/**
 * @author dengzebiao
 * @since 2019.6.4
 * 背包业务使用类
 * */
public class Backpack {
    /** 背包实体类 */
    private BackpackEnt backpackEnt;

    /** 背包道具储存 */
    public void putBackpackItem(BackpackItem backpackItem){
        backpackEnt.getBackpackInfo().getBackpackItems().put(backpackItem.getId(),backpackItem);
    }

    /** 背包道具获取 */
    public BackpackItem getBackpackItem(int id){
        return backpackEnt.getBackpackInfo().getBackpackItems().get(id);
    }

    /** 背包道具移除 */
    public void removeBackpackItem(int id){
        backpackEnt.getBackpackInfo().getBackpackItems().remove(id);
    }

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
}
