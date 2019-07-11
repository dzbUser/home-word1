package com.aiwan.server.user.role.skill.model;

import com.aiwan.server.user.role.fight.context.SkillUseContext;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.skill.impact.ImpactInterface;
import com.aiwan.server.user.role.skill.resource.SkillLevelResource;
import com.aiwan.server.user.role.skill.resource.SkillResource;
import com.aiwan.server.util.GetBean;

import java.util.List;

/**
 * 技能抽象类
 *
 * @author dengzebiao
 * @since 2019.7.1
 */
public class Skill {

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

    public static Skill valueOf(int skillId, int skillLevel) {
        Skill skill = new Skill();
        skill.setSkillId(skillId);
        skill.setSkillLevel(skillLevel);
        return skill;
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
    public SkillLevelResource getSkillLevelResource() {
        return GetBean.getSkillManager().getSkillLevelReById(skillId, skillLevel);
    }

    /**
     * 使用技能
     */
    public void doUserSkill(BaseUnit active, List<BaseUnit> passiveList) {
        SkillUseContext skillUseContext = new SkillUseContext();
        //循环调用技能效果
        for (Integer type : getSkillLevelResource().getImpactList()) {
            ImpactInterface impactInterface = getSkillLevelResource().getImpactTypeMap().get(type).creator();
            for (BaseUnit passive : passiveList) {
                impactInterface.takeImpact(active, passive, getSkillLevelResource(), skillUseContext);
            }
        }

    }
}
