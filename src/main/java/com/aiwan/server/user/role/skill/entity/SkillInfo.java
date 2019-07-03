package com.aiwan.server.user.role.skill.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.7.1
 * 技能详细数据
 */
public class SkillInfo {
    /**
     * 角色技能栏
     */
    private Integer[] skills;

    /**
     * 角色学习技能容器
     */
    private Map<Integer, SkillMessage> skillMessageMap = new HashMap<Integer, SkillMessage>();

    public SkillInfo(int num) {
        this.skills = new Integer[num];
    }

    public SkillInfo() {
    }

    public Integer[] getSkills() {
        return skills;
    }

    public void setSkills(Integer[] skills) {
        this.skills = skills;
    }

    public Map<Integer, SkillMessage> getSkillMessageMap() {
        return skillMessageMap;
    }

    public void setSkillMessageMap(Map<Integer, SkillMessage> skillMessageMap) {
        this.skillMessageMap = skillMessageMap;
    }
}
