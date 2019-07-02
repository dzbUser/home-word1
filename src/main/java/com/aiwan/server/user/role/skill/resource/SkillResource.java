package com.aiwan.server.user.role.skill.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.publicsystem.annotation.Resource;

/**
 * 技能静态资源
 */
@Resource("staticresource/skill.xls")
public class SkillResource {
    /**
     * 技能id
     */
    @CellMapping(name = "skillId")
    private int skillId;

    /**
     * 技能名字
     */
    @CellMapping(name = "skillName")
    private String skillName;

    /**
     * 技能等级
     */
    @CellMapping(name = "skillLevel")
    private int skillLevel;

    /**
     * 是否为buff技能
     */
    @CellMapping(name = "isBuffSkill")
    private int isBuffSkill;
    /**
     * 技能最高等级
     */
    @CellMapping(name = "maxLevel")
    private int maxLevel;

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getIsBuffSkill() {
        return isBuffSkill;
    }

    public void setIsBuffSkill(int isBuffSkill) {
        this.isBuffSkill = isBuffSkill;
    }
}
