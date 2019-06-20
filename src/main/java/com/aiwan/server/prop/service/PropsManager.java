package com.aiwan.server.prop.service;

import com.aiwan.server.prop.resource.PropsResource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dengzebiao
 * @since 2019.6.3
 * 静态资源管理类
 * */
@Component
public class PropsManager {
    public final static int EQUIP  = 3;
    public final static int Prop = 1;
    public final static int DEVELOP = 2;

    /** 道具静态资源映射 */
    private Map<Integer, PropsResource> propsMap = new ConcurrentHashMap<Integer, PropsResource>();

    /**
     * 获取某种道具
     * */
    public PropsResource getPropsResource(Integer id) {
        return propsMap.get(id);
    }

    /**
     * 保存某类道具
     * */
    public void putProps(PropsResource propsResource){
        propsMap.put(propsResource.getId(), propsResource);
    }

}
