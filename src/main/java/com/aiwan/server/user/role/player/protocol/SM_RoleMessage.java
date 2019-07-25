package com.aiwan.server.user.role.player.protocol;

import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;

import java.io.Serializable;
import java.util.Map;

/** 角色信息返回类 */
public class SM_RoleMessage implements Serializable {

    /** 职业 */
    private int job;

    /** 性别*/
    private int sex;

    /** 等级 */
    private int level;

    /** 升级所需经验 */
    private int needExperience;

    /** 经验 */
    private int experience;

    /**
     * 用户名字
     */
    private String name;

    /** 属性 */
    private Map<AttributeType, AttributeElement> map;

    /**
     * 战力
     */
    private long combatPower;

    /** 创建 */
    public static SM_RoleMessage valueOf(int job, int sex, int level, int experience, int needExperience, Map<AttributeType, AttributeElement> map, String name, long combatPower) {
        SM_RoleMessage sm_roleMessage = new SM_RoleMessage();
        sm_roleMessage.setJob(job);
        sm_roleMessage.setExperience(experience);
        sm_roleMessage.setSex(sex);
        sm_roleMessage.setLevel(level);
        sm_roleMessage.setNeedExperience(needExperience);
        sm_roleMessage.setMap(map);
        sm_roleMessage.setName(name);
        sm_roleMessage.setCombatPower(combatPower);
        return sm_roleMessage;
    }

    public int getJob() {
        return job;
    }

    public SM_RoleMessage setJob(int job) {
        this.job = job;
        return this;
    }

    public int getSex() {
        return sex;
    }

    public SM_RoleMessage setSex(int sex) {
        this.sex = sex;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public SM_RoleMessage setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getNeedExperience() {
        return needExperience;
    }

    public SM_RoleMessage setNeedExperience(int needExperience) {
        this.needExperience = needExperience;
        return this;
    }

    public int getExperience() {
        return experience;
    }

    public SM_RoleMessage setExperience(int experience) {
        this.experience = experience;
        return this;
    }

    public Map<AttributeType, AttributeElement> getMap() {
        return map;
    }

    public SM_RoleMessage setMap(Map<AttributeType, AttributeElement> map) {
        this.map = map;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCombatPower() {
        return combatPower;
    }

    public void setCombatPower(long combatPower) {
        this.combatPower = combatPower;
    }
}
