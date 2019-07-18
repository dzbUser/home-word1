package com.aiwan.server.user.role.team.protocol;

import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;

/**
 * 队伍信息
 */
public class TeamMessage implements Serializable {

    /**
     * 队伍ID
     */
    private long id;

    /**
     * 队长名字
     */
    private String name;

    /**
     * 队员数量
     */
    private int num;

    public static TeamMessage valueOf(long id, String name, int num) {
        TeamMessage teamMessage = new TeamMessage();
        teamMessage.setId(id);
        teamMessage.setName(name);
        teamMessage.setNum(num);
        return teamMessage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
