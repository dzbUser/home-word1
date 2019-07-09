package com.aiwan.server.user.role.fight.service;

import com.aiwan.server.user.role.skill.model.AbstractSkill;

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
     * @param aimType   目标类型
     */
    void doUserSkill(Long activeRid, Long passiveId, AbstractSkill skill, int mapId, int aimType);


    /**
     * 使用技能
     *
     * @param activeRid 角色id
     * @param passiveId 目标id
     * @param position  技能位置
     * @param aimType   目标类型
     */
    void userSkill(Long activeRid, Long passiveId, int position, int aimType);
}
