package com.aiwan.server.role.player.rule;

/**
 * @author dengzebiao
 * 角色规则信息（暂用）
 * */
public class RoleRule {
    private static String[] jobs = new String[2];
    static {
        jobs[0] = "战士";
        jobs[1] = "法师";
    }
    public static String getJob(int job){
        if (job<jobs.length) {
            return jobs[job];
        }
        return "";
    }
}
