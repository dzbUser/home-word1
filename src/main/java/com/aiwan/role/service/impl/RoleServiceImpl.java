package com.aiwan.role.service.impl;

import com.aiwan.publicsystem.common.Session;
import com.aiwan.role.service.RoleManager;
import com.aiwan.role.service.RoleService;
import com.aiwan.user.protocol.CM_CreateRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleManager roleManager;


    @Override
    public void create(Session session, CM_CreateRole cm_createRole) {

    }
}
