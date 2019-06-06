package com.aiwan.server.role.mount.service.impl;

import com.aiwan.server.role.mount.service.MountManager;
import com.aiwan.server.role.mount.service.MountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MountServiceImpl implements MountService {
    @Autowired
    private MountManager mountManager;

    @Override
    public void createMount(Long rId) {
        mountManager.createMount(rId);
    }
}
