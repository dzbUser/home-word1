package com.aiwan.scenes.service;

import com.aiwan.scenes.mapresource.MapResource;
import com.aiwan.scenes.mapresource.PositionMeaning;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 地图管理
 * @author dengzebiao
 * @since 2019.5.17
 * */
@Component("mapManager")
public class MapManager {
    //存地图
    private Map<Integer, MapResource> mapResourceMap = new HashMap<>();
    //存地图数字涵义协议
    private Map<Integer, PositionMeaning> mapProtocolMap = new HashMap<>();

    //获取地图
    public MapResource getMapResource(Integer mapType){
        return mapResourceMap.get(mapType);
    }

    //获取地图位置含义名字
    public String getmapProtocol(Integer num){
        return mapProtocolMap.get(num).getName();
    }

    //获取地图位置含义
    public PositionMeaning getPositionMeaning(Integer num){
        return mapProtocolMap.get(num);
    }

    //存入地图资源
    public void putMapResource(MapResource mapResource){

        mapResourceMap.put(mapResource.getMapType(),mapResource);
    }

    //存入地图协议
    public void putMapProtocol(Integer num,PositionMeaning positionMeaning){
        mapProtocolMap.put(num,positionMeaning);
    }

}
