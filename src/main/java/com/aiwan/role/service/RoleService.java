package com.aiwan.role.service;

import com.aiwan.publicsystem.common.Session;
import com.aiwan.user.protocol.CM_CreateRole;

public interface RoleService {
    public void create(Session session, CM_CreateRole cm_createRole);
}
