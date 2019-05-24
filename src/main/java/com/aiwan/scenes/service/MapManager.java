package com.aiwan.scenes.service;

import com.aiwan.scenes.mapresource.MapResource;
//import com.aiwan.scenes.mapresource.PositionMeaning;
//import com.aiwan.user.entity.User;
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

    //获取地图
    public MapResource getMapResource(Integer mapType){
        return mapResourceMap.get(mapType);
    }


    //存入地图资源
    public void putMapResource(MapResource mapResource){

        mapResourceMap.put(mapResource.getMapType(),mapResource);
    }



}
