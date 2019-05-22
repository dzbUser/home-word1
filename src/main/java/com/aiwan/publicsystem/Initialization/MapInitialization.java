package com.aiwan.publicsystem.Initialization;

import com.aiwan.scenes.mapresource.MapResource;
import com.aiwan.scenes.service.MapManager;
import com.aiwan.util.ExcelUtil;
import com.aiwan.util.GetBean;

import java.io.*;
import java.util.List;

/**
 * 地图资源初始化类
 * */
public class MapInitialization {
    private final static String FILEPATH="src/main/resources/map1/map.xls";
    public static void init() throws IOException, InstantiationException, IllegalAccessException {
        System.out.println("测试开");
        List<MapResource> list = ExcelUtil.analysisExcelFile(FILEPATH, MapResource.class);
        MapManager mapManager = GetBean.getMapManager();
        MapResource mapResource = list.get(0);
        for (int i = 0;i < list.size();i++){
            list.get(i).init();
            mapManager.putMapResource(list.get(i));
        }
    }


}
