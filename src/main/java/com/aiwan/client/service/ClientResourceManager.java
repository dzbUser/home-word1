package com.aiwan.client.service;

import com.aiwan.client.Resource.ClientViewResource;
import com.aiwan.client.init.ClientResourceInit;
import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.util.ExcelUtil;
import com.aiwan.server.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.13
 * 客户端资源管理类
 * */
@Manager
public class ClientResourceManager {
    static Logger logger = LoggerFactory.getLogger(ClientResourceManager.class);

    /** 资源存储类 */
    @Static(initializeMethodName = "initPromptResource")
    private static Map<String,Map<Integer, ClientViewResource>> resourceMap = new HashMap<String,Map<Integer, ClientViewResource>>(16);

    public static String getContent(String type,int id){
        if (ClientResourceManager.resourceMap.get(type) == null) {
            return null;
        }
        return ClientResourceManager.resourceMap.get(type).get(id).getContent();
    }

    public static Map<String, Map<Integer, ClientViewResource>> getResourceMap() {
        return ClientResourceManager.resourceMap;
    }

    public static void setResourceMap(Map<String, Map<Integer, ClientViewResource>> resourceMap) {
        ClientResourceManager.resourceMap = resourceMap;
    }

    /**
     * 提示信息初始化
     */
    private void initPromptResource() {
        String path = ResourceUtil.getResourcePath(ClientViewResource.class);
        List<ClientViewResource> list = new ArrayList<ClientViewResource>();
        logger.debug("FILEPATH：{}", path);
        logger.debug("初始化客户端查看资源");
        try {
            list.addAll(ExcelUtil.analysisWithRelativePath(path, ClientViewResource.class));
        } catch (IllegalAccessException e) {
            logger.error(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            logger.error(e.getLocalizedMessage());
        }

        for (ClientViewResource clientViewResource : list) {
            //模块类型为空
            if (ClientResourceManager.getResourceMap().get(clientViewResource.getType()) == null) {
                Map<Integer, ClientViewResource> map = new HashMap<Integer, ClientViewResource>(16);
                ClientResourceManager.getResourceMap().put(clientViewResource.getType(), map);
            }
            resourceMap.get(clientViewResource.getType()).put(clientViewResource.getId(), clientViewResource);
        }
    }
}
