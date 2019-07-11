package com.aiwan.server.user.role.skill.entity;

import com.aiwan.server.user.role.skill.service.SkillManager;

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
    private Map<Integer, SkillElement> skillMessageMap = new HashMap<Integer, SkillElement>();

    public SkillInfo(int num) {
        this.skills = new Integer[num];
        //学习普通攻击
        skillMessageMap.put(SkillManager.ORDINARY_ATTACK_ID, SkillElement.valueOf(SkillManager.ORDINARY_ATTACK_ID, 1));
    }

    public SkillInfo() {
    }

    public Integer[] getSkills() {
        return skills;
    }

    public void setSkills(Integer[] skills) {
        this.skills = skills;
    }

    public Map<Integer, SkillElement> getSkillMessageMap() {
        return skillMessageMap;
    }

    public void setSkillMessageMap(Map<Integer, SkillElement> skillMessageMap) {
        this.skillMessageMap = skillMessageMap;
    }
}
