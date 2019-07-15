package com.aiwan.server.user.role.fight.protocol;


import java.io.Serializable;

/**
 * 用户状态更新信息
 *
 * @author dengzebiao
 * @since 2019.7.15
 */
public class SM_UnitStatusMessage implements Serializable {

    /**
     * 唯一id
     */
    private long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 最大血量
     */
    private long maxHp;

    /**
     * 当前血量
     */
    private long currentHp;

    public static SM_UnitStatusMessage valueOf(long id, String name, long maxHp, long currentHp) {
        SM_UnitStatusMessage sm_unitStatusMessage = new SM_UnitStatusMessage();
        sm_unitStatusMessage.setCurrentHp(currentHp);
        sm_unitStatusMessage.setId(id);
        sm_unitStatusMessage.setMaxHp(maxHp);
        sm_unitStatusMessage.setName(name);
        return sm_unitStatusMessage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(long maxHp) {
        this.maxHp = maxHp;
    }

    public long getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(long currentHp) {
        this.currentHp = currentHp;
    }
}
