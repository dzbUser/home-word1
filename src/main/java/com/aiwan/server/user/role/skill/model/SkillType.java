package com.aiwan.server.user.role.skill.model;

import com.aiwan.server.user.role.skill.model.impl.CommonSkill;
import com.aiwan.server.user.role.skill.model.impl.CuretSkill;
import com.aiwan.server.user.role.skill.model.impl.GroupAttackSkill;

public enum SkillType {

    /**
     * 普通技能
     */
    COMMON(0, CommonSkill.class),

    /**
     * 治疗技能
     */
    CURE(1, CuretSkill.class),

    /**
     * 群体攻击
     */
    GROUP_ATTACK(2, GroupAttackSkill.class);

    /**
     * 技能id
     */
    int id;

    /**
     * 技能类型类
     */
    private Class<? extends AbstractSkill> skillClass;

    SkillType(int id, Class<? extends AbstractSkill> skillClass) {
        this.id = id;
        this.skillClass = skillClass;
    }

    public AbstractSkill creator() {
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

    public static AbstractSkill getSkillById(int id) {
        for (SkillType skillType : values()) {
            if (skillType.getId() == id) {
                return skillType.creator();
            }
        }
        return null;
    }
}
