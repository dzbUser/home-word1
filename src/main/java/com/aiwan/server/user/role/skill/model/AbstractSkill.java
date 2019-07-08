package com.aiwan.server.user.role.skill.model;

import com.aiwan.server.scenes.fight.model.pvpunit.FighterRole;
import com.aiwan.server.user.role.skill.resource.SkillLevelResource;
import com.aiwan.server.user.role.skill.resource.SkillResource;
import com.aiwan.server.util.GetBean;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 技能抽象类
 *
 * @author dengzebiao
 * @since 2019.7.1
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
public abstract class AbstractSkill {

    /**
     * 技能id
     */
    private int skillId;

    /**
     * 技能等级
     */
    private int skillLevel;

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    /**
     * 初始化
     */
    public void init(int skillId, int level) {
        setSkillId(skillId);
        setSkillLevel(level);
    }

    /**
     * 获取技能静态资源
     */
    public SkillResource getResource() {
        return GetBean.getSkillManager().getSkillResourceBySkillId(getSkillId());
    }


    /**
     * 获取对应等级的静态资源
     */
    public SkillLevelResource getSkillLevelResouece() {
        return GetBean.getSkillManager().getSkillLevelReById(skillId, skillLevel);
    }

//    /**
//     * 使用技能
//     * */
//    protected abstract void doUserSkill(FighterRole attater);
}
