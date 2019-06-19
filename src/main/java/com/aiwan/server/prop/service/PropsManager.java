package com.aiwan.server.prop.service;

import com.aiwan.server.prop.resource.EquipmentResource;
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
    /** 装备静态资源映射类 */
    private Map<Integer, EquipmentResource> equipmentMap = new ConcurrentHashMap<Integer, EquipmentResource>();

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

    /**
     *获取某类装备
     * */
    public EquipmentResource getEquipment(Integer id){
        return equipmentMap.get(id);
    }

    /**
     * 保存某类装备
     * */
    public void putEquipment(EquipmentResource equipmentResource){
        equipmentMap.put(equipmentResource.getId(), equipmentResource);
    }
}
