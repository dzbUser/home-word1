package com.aiwan.server.publicsystem.Initialization;

import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.service.MapManager;
import com.aiwan.server.util.ExcelUtil;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * @author dengzebiao
 * 地图资源初始化类
 * */
public class MapInitialization {
    private static final Logger logger = LoggerFactory.getLogger(MapInitialization.class);
    private final static String FILEPATH = "staticresource/map.xls";
    public static void init() throws IOException, InstantiationException, IllegalAccessException {

        //加载资源
        logger.debug("开始加载地图静态资源");
        List<MapResource> list = ExcelUtil.analysisWithRelativePath(FILEPATH, MapResource.class);
        MapManager mapManager = GetBean.getMapManager();
        for (int i = 0;i < list.size();i++){
            list.get(i).init();
            mapManager.putMapResource(list.get(i));
        }
        mapManager.init();
    }


}
