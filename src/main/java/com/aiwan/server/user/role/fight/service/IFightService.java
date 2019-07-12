package com.aiwan.server.user.role.fight.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.fight.pvpUnit.BaseUnit;
import com.aiwan.server.user.role.skill.model.Skill;

/**
 * 战斗业务接口
 *
 * @author dengzebiao
 * @since 2019.7.9
 */
public interface IFightService {

    /**
     * 释放技能
     *
     * @param activeRid 角色id
     * @param passiveId 目标id
     * @param skill     技能
     * @param mapId     场景
     */
    void doUserSkill(Long activeRid, Long passiveId, Skill skill, int mapId);


    /**
     * 使用技能
     *
     * @param activeRid 角色id
     * @param passiveId 目标id
     * @param position  技能位置
     */
    void userSkill(Long activeRid, Long passiveId, int position);

    /**
     * 是否在范围内
     *
     * @param activeRole  目标1
     * @param passiveRole 目标2
     * @param distance    巨鹿
     * @return
     */
    boolean isDistanceSatisfy(BaseUnit activeRole, BaseUnit passiveRole, int distance);

    /**
     * 查看战斗buff
     *
     * @param rId     角色id
     * @param session 会话
     */
    void viewFightBuff(Long rId, Session session);
}
