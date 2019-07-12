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
     * 技能描述
     */
    @CellMapping(name = "dec")
    private String dec;

    /**
     * 是否为群体攻击
     */
    @CellMapping(name = "isGroup")
    private int isGroup;


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

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public int getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(int isGroup) {
        this.isGroup = isGroup;
    }
}
