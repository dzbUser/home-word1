package com.aiwan.user.model;

import java.util.List;

public class UserBaseInfo {
    private List<Long> roles;

    public List<Long> getRoles() {
        return roles;
    }

    public UserBaseInfo setRoles(List<Long> roles) {
        this.roles = roles;
        return this;
    }
}
