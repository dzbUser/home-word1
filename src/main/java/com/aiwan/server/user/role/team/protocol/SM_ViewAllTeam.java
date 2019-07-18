package com.aiwan.server.user.role.team.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 查看所有队伍
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
public class SM_ViewAllTeam implements Serializable {

    /**
     * 队伍列表
     */
    List<TeamMessage> list;

    public static SM_ViewAllTeam valueOf(List<TeamMessage> list) {
        SM_ViewAllTeam sm_viewAllTeam = new SM_ViewAllTeam();
        sm_viewAllTeam.setList(list);
        return sm_viewAllTeam;
    }

    public List<TeamMessage> getList() {
        return list;
    }

    public void setList(List<TeamMessage> list) {
        this.list = list;
    }
}
