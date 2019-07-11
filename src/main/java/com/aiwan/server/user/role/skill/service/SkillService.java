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

    /**
     * 查看所学技能
     *
     * @param rId 角色id
     */
    void viewLearnedSkill(Long rId, Session session);

    /**
     * 技能升级
     *
     * @param skillId 技能id
     * @param rId     角色id
     * @param session 会话
     */
    void upgradeSkill(Long rId, int skillId, Session session);

    /**
     * 移动技能到技能栏某个位置
     *
     * @param rId      角色id
     * @param position 技能栏位置
     * @param skillId  技能id
     */
    void moveSkillToPosition(Long rId, int skillId, int position, Session session);

    /**
     * 查看技能栏
     */
    void viewSkillBar(Long rid, Session session);
}
