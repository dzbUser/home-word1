package com.aiwan.server.user.role.team.protocol;

import java.util.List;

/**
 * 查看队伍所有成员
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
public class SM_ViewMemberInTeam {

    /**
     * 队长id
     */
    private long leaderId;

    List<MemberMessage> list;

    public static SM_ViewMemberInTeam valueOf(long leaderId, List<MemberMessage> list) {
        SM_ViewMemberInTeam sm_viewMemberInTeam = new SM_ViewMemberInTeam();
        sm_viewMemberInTeam.setLeaderId(leaderId);
        sm_viewMemberInTeam.setList(list);
        return sm_viewMemberInTeam;
    }

    public long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(long leaderId) {
        this.leaderId = leaderId;
    }

    public List<MemberMessage> getList() {
        return list;
    }

    public void setList(List<MemberMessage> list) {
        this.list = list;
    }
}
