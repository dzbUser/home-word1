package com.aiwan.server.user.role.team.protocol;

import java.io.Serializable;

/**
 * 队伍业务中信息类
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
public class MemberMessage implements Serializable {

    /**
     * 用户id
     */
    private long rId;

    /**
     * 用户名字
     */
    private String name;

    /**
     * 职业
     */
    private int job;

    /**
     * 用户等级
     */
    private int level;


    public static MemberMessage valueOf(long rId, String name, int level, int job) {
        MemberMessage memberMessage = new MemberMessage();
        memberMessage.setrId(rId);
        memberMessage.setLevel(level);
        memberMessage.setName(name);
        memberMessage.setJob(job);
        return memberMessage;
    }

    public long getrId() {
        return rId;
    }

    public void setrId(long rId) {
        this.rId = rId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }
}
