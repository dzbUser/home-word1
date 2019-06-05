package com.aiwan.server.prop.service;

import com.aiwan.server.prop.resource.Equipment;
import com.aiwan.server.prop.resource.Props;
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
    private Map<Integer, Props> propsMap = new ConcurrentHashMap<>();
    /** 装备静态资源映射类 */
    private Map<Integer, Equipment> equipmentMap = new ConcurrentHashMap<>();

    /**
     * 获取某种道具
     * */
    public Props getProps(Integer id){
        return propsMap.get(id);
    }

    /**
     * 保存某类道具
     * */
    public void putProps(Props props){
        propsMap.put(props.getId(),props);
    }

    /**
     *获取某类装备
     * */
    public Equipment getEquipment(Integer id){
        return equipmentMap.get(id);
    }

    /**
     * 保存某类装备
     * */
    public void putEquipment(Equipment equipment){
        equipmentMap.put(equipment.getId(),equipment);
    }
}
