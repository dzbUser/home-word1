package com.aiwan.server.user.role.skill.model;

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
    private Skill[] skills;

    /**
     * 角色学习技能容器
     */
    private Map<Integer, Skill> skillMap = new HashMap<Integer, Skill>();

    public SkillInfo(int num) {
        this.skills = new Skill[num];
    }

    public Skill[] getSkills() {
        return skills;
    }

    public void setSkills(Skill[] skills) {
        this.skills = skills;
    }

    public Map<Integer, Skill> getSkillMap() {
        return skillMap;
    }

    public void setSkillMap(Map<Integer, Skill> skillMap) {
        this.skillMap = skillMap;
    }
}
