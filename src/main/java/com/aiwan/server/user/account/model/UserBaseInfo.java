package com.aiwan.server.user.account.model;

import java.util.List;

/**
 * @author dengzebiao
 * 用户实体类信息
 * */
public class UserBaseInfo {
    /** 角色id集合 */
    private List<Long> roles;

    public List<Long> getRoles() {
        return roles;
    }

    public UserBaseInfo setRoles(List<Long> roles) {
        this.roles = roles;
        return this;
    }
}
