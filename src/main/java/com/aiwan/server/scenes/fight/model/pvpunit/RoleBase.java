package com.aiwan.server.scenes.fight.model.pvpunit;

import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.user.role.skill.entity.SkillInfo;

public class RoleBase {

    /**
     * 所属账号
     */
    private String accountId;

    /**
     * 角色名字
     */
    private String name;


    public static RoleBase valueOf(String accountId, String name) {
        RoleBase roleBase = new RoleBase();
        roleBase.setAccountId(accountId);
        roleBase.setName(name);
        return roleBase;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
