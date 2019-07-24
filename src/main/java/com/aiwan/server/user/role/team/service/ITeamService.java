package com.aiwan.server.user.role.team.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.team.model.TeamModel;

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

    /**
     * 查看所有队伍
     *
     * @param session 会话
     */
    void viewAllTeam(Session session);

    /**
     * 申请加入队伍
     *
     * @param teamId  队伍id
     * @param session 会话
     */
    void applyJoin(long teamId, Session session);

    /**
     * 离开队伍
     *
     * @param rid 退出用户id
     */
    void leaveTeam(long rid);

    /**
     * 查看所有申请
     *
     * @param session 会话
     */
    void viewAllApplication(Session session);

    /**
     * 允许加入队伍
     *
     * @param allowRId 申请者id
     * @param session  会话
     */
    void allowJoin(long allowRId, Session session);

    /**
     * 查看队伍成员
     *
     * @param session
     */
    void viewMemberInTeam(Session session);

    /**
     * 踢出队伍
     *
     * @param kickOutRId 踢出者id
     * @param session    会话
     */
    void kickOut(long kickOutRId, Session session);

    /**
     * 加入队伍
     *
     * @param applyRole 申请者
     * @param teamModel 队伍
     */
    public void joinTeam(Role applyRole, TeamModel teamModel);

    /**
     * 被踢出队伍
     *
     * @param kickOutRole 被踢出的队员
     * @param teamModel   队伍
     */
    public void beKickOut(Role kickOutRole, TeamModel teamModel);
    
}
