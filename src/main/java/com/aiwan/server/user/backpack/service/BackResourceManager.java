package com.aiwan.server.user.backpack.service;

import com.aiwan.server.user.backpack.resource.BackResource;
import org.springframework.stereotype.Service;

/**
 * @author dengzebiao
 * @since 2019.6.24
 * 静态资源管理类
 */
@Service
public class BackResourceManager {

    /**
     * 背包静态资源
     */
    private BackResource backResource;

    public BackResource getBackResource() {
        return backResource;
    }

    public void setBackResource(BackResource backResource) {
        this.backResource = backResource;
    }
}
