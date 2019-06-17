package com.aiwan.client.service;

import com.aiwan.client.Resource.ClientViewResource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.13
 * 客户端资源管理类
 * */
public class ClientResourseManager {

    /** 资源存储类 */
    private static Map<String,Map<Integer, ClientViewResource>> resourceMap = new HashMap<String,Map<Integer, ClientViewResource>>(16);

    public static String getContent(String type,int id){
        if (ClientResourseManager.resourceMap.get(type) == null){
            return null;
        }
        return ClientResourseManager.resourceMap.get(type).get(id).getContent();
    }

    public static Map<String, Map<Integer, ClientViewResource>> getResourceMap() {
        return ClientResourseManager.resourceMap;
    }

    public static void setResourceMap(Map<String, Map<Integer, ClientViewResource>> resourceMap) {
        ClientResourseManager.resourceMap = resourceMap;
    }
}
