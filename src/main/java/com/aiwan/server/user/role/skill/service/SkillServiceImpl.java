package com.aiwan.server.user.role.skill.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.skill.model.Skill;
import com.aiwan.server.user.role.skill.model.SkillModel;
import com.aiwan.server.user.role.skill.resource.SkillLevelResource;
import com.aiwan.server.user.role.skill.resource.SkillResource;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 技能业务实现类
 *
 * @author dengzebiao
 * @since 2019.7.1
 */
public class SkillServiceImpl implements SkillService {

    private static Logger logger = LoggerFactory.getLogger(SkillServiceImpl.class);

    @Autowired
    private SkillManager skillManager;

    @Override
    public void learnSkill(Long rId, int skillId, Session session) {
        /*
         * 1.是否有该技能
         * 2.角色等级是否达到要求
         * 3.是否以学过该技能
         * */
        SkillResource skillResource = skillManager.getSkillResourceBySkillId(skillId);
        if (skillResource == null) {
            //没有该技能
            session.sendPromptMessage(PromptCode.NOSKILL, "");
            return;
        }

        //获取等级资源
        SkillLevelResource skillLevelResource = skillManager.getSkillLevelReById(skillId + "_1");
        //获取人物
        Role role = GetBean.getRoleManager().load(rId);
        if (skillLevelResource.getRoleLevelDemand() > role.getLevel()) {
            //角色等级未达到要求
            session.sendPromptMessage(PromptCode.NOTREACHEDLEVELDEMAND, "");
            return;
        }

        SkillModel skillModel = skillManager.load(rId);
        Skill skill = skillModel.getSkillBySkillId(skillId);
        if (skill != null) {
            //技能已经学过
            session.sendPromptMessage(PromptCode.HAVALEARN, "");
            return;
        }

        //学习该技能
        Skill skill =
                skillModel.putSkill()
    }
}
