package com.aiwan.server.user.role.player.service.impl;

import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.user.role.player.resource.RoleResource;
import com.aiwan.server.util.ExcelUtil;
import com.aiwan.server.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengzebiao
 * @since 2019.6.10
 * 人物资源管理类
 * */
@Service
public class RoleResourceManager {
    static Logger logger = LoggerFactory.getLogger(RoleResourceManager.class);

    @Static(initializeMethodName = "initRoleResource")
    private RoleResource roleResource;

    public RoleResource getRoleResource() {
        return roleResource;
    }

    public RoleResourceManager setRoleResource(RoleResource roleResource) {
        this.roleResource = roleResource;
        return this;
    }



    /** 获取人物最高等级 */
    public int getMaxLevel() {
        return roleResource.getMaxLevel();
    }

    /**
     * 任务静态资源初始化
     */
    private void initRoleResource() throws InstantiationException, IllegalAccessException {
        //获取静态资源路径
        String path = ResourceUtil.getResourcePath(RoleResource.class);
        logger.info("初始化任务静态资源");
        List<RoleResource> roleList = null;
        roleList = ExcelUtil.analysisWithRelativePath(path, RoleResource.class);
        roleList.get(0).init();
        setRoleResource(roleList.get(0));
    }

}
