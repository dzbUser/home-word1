package com.aiwan.server.user.role.team.service;

import com.aiwan.server.user.role.player.model.Role;

/**
 * 队伍业务接口
 *
 * @author dengzebiao
 * @since 2019.7.17
 */
public interface ITeamService {

    /**
     * 创建队伍
     *
     * @param role 创建角色
     */
    void createTeam(Role role);
}
