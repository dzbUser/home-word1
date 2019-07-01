package com.aiwan.server.user.backpack.service;

import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.user.backpack.resource.BackResource;
import com.aiwan.server.util.ExcelUtil;
import com.aiwan.server.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengzebiao
 * @since 2019.6.24
 * 静态资源管理类
 */
@Service
public class BackResourceManager {

    static Logger logger = LoggerFactory.getLogger(BackResourceManager.class);

    /**
     * 背包静态资源
     */
    @Static(initializeMethodName = "initBackResource")
    private BackResource backResource;

    public BackResource getBackResource() {
        return backResource;
    }

    public void setBackResource(BackResource backResource) {
        this.backResource = backResource;
    }

    private void initBackResource() {
        //获取静态资源路径
        String path = ResourceUtil.getResourcePath(BackResource.class);
        logger.info("初始化任务静态资源");
        List<BackResource> list = null;
        try {
            list = ExcelUtil.analysisWithRelativePath(path, BackResource.class);
        } catch (IllegalAccessException e) {
            logger.error(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            logger.error(e.getLocalizedMessage());
        }
        setBackResource(list.get(0));
    }
}
