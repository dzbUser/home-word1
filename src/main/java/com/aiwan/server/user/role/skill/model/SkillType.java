package com.aiwan.server.user.role.skill.model;

import com.aiwan.server.user.role.skill.model.impl.CommonSkill;

public enum SkillType {

    /**
     * 普通技能
     */
    COMMON(0, CommonSkill.class);

    /**
     * 技能id
     */
    int id;

    /**
     * 技能类型类
     */
    private Class<? extends Skill> skillClass;

    SkillType(int id, Class<? extends Skill> skillClass) {
        this.id = id;
        this.skillClass = skillClass;
    }

    public Skill creator() {
        try {
            return skillClass.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("生成技能实例{" + skillClass.getName() + "}失败");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Skill getSkillById(int id) {
        for (SkillType skillType : values()) {
            if (skillType.getId() == id) {
                return skillType.creator();
            }
        }
        return null;
    }
}