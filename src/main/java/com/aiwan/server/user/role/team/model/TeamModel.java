package com.aiwan.server.user.role.team.model;

import com.aiwan.server.publicsystem.model.GameObject;
import com.aiwan.server.util.IDUtil;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 队伍模型
 *
 * @author dengzebiao
 * @since 2019.7.17
 */
public class TeamModel extends GameObject {

    /**
     * 队长ID
     */
    private long leaderId;

    /**
     * 队伍列表
     */
    private List<Long> TeamList = Collections.synchronizedList(new ArrayList<>());

    /**
     * 申请列表
     */
    private List<Long> Application = Collections.synchronizedList(new LinkedList<>());

    public static TeamModel valueOf(long leaderId) {
        TeamModel teamModel = new TeamModel();
        teamModel.leaderId = leaderId;
        teamModel.setObjectId(IDUtil.getId());
        teamModel.getTeamList().add(leaderId);
        return teamModel;
    }

    public long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(long leaderId) {
        this.leaderId = leaderId;
    }

    public List<Long> getTeamList() {
        return TeamList;
    }

    public void setTeamList(List<Long> teamList) {
        TeamList = teamList;
    }

    public List<Long> getApplication() {
        return Application;
    }

    public void setApplication(List<Long> application) {
        Application = application;
    }
}
