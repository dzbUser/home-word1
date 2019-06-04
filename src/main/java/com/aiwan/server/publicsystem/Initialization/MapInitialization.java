package com.aiwan.server.publicsystem.Initialization;

import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.service.MapManager;
import com.aiwan.server.util.ExcelUtil;
import com.aiwan.server.util.GetBean;

import java.io.*;
import java.util.List;

/**
 * @author dengzebiao
 * 地图资源初始化类
 * */
public class MapInitialization {
    private final static String FILEPATH="src/main/resources/map1/map.xls";
    public static void init() throws IOException, InstantiationException, IllegalAccessException {

        List<MapResource> list = ExcelUtil.analysisExcelFile(FILEPATH, MapResource.class);
        MapManager mapManager = GetBean.getMapManager();
        for (int i = 0;i < list.size();i++){
            list.get(i).init();
            mapManager.putMapResource(list.get(i));
        }
        mapManager.init();
    }


}
