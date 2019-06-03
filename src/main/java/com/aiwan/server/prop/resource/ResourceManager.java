package com.aiwan.server.prop.resource;

import com.aiwan.server.util.Protocol;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dengzebiao
 * @since 2019.6.3
 * 静态资源管理类
 * */
@Component
public class ResourceManager {
    /** 道具静态资源映射 */
    private Map<Integer,Props> propsMap = new ConcurrentHashMap<>();
    /** 装备静态资源映射类 */
    private Map<Integer,Equipment> equipmentMap = new ConcurrentHashMap<>();

    /**
     * 获取某种道具
     * */
    public Props getProps(Integer id){
        return propsMap.get(id);
    }

    /**
     * 保存某类道具
     * */
    public void putProps(Integer id,Props props){
        propsMap.put(id,props);
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
    public void putEquipment(Integer id,Equipment equipment){
        equipmentMap.put(id,equipment);
    }
}
