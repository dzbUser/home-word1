package com.aiwan.server.user.role.skill.service;

import com.aiwan.server.publicsystem.common.Session;

/**
 * 技能业务接口
 *
 * @author dengzebiao
 * @since 2019.7.1
 */
public interface SkillService {

    /**
     * 学习技能
     *
     * @param rId     角色id
     * @param skillId 技能id
     * @param session 会话
     */
    void learnSkill(Long rId, int skillId, Session session);
}
