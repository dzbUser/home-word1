package com.aiwan.server.monster.service;

import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.util.ExcelUtil;
import com.aiwan.server.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 怪物管理类
 *
 * @author dengezbiao
 * @since 2019.7.4
 */
@Manager
public class MonsterManager {

    Logger logger = LoggerFactory.getLogger(MonsterManager.class);

    /**
     * 地图id与怪物的映射
     */
    @Static(initializeMethodName = "initMapMonsterMap")
    private Map<Integer, List<MonsterResource>> mapMonsterMap = new HashMap<>();

    /**
     * 资源id与怪物资源的映射
     */
    private Map<Integer, MonsterResource> monsterMap = new HashMap<>();

    /**
     * 初始化静态资源映射
     */
    private void initMapMonsterMap() {
        //获取地图资源静态路径
        String path = ResourceUtil.getResourcePath(MonsterResource.class);
        //加载资源
        logger.debug("开始加载怪物静态资源");
        List<MonsterResource> list = null;
        try {
            list = ExcelUtil.analysisWithRelativePath(path, MonsterResource.class);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        for (MonsterResource monsterResource : list) {
            if (monsterResource.getMap() == 0) {
                monsterResource.init();
                monsterMap.put(monsterResource.getResourceId(), monsterResource);
                continue;
            }
            //初始化地图id与怪物的映射
            List<MonsterResource> monsterList = mapMonsterMap.get(monsterResource.getMap());
            monsterResource.init();
            if (monsterList == null) {
                monsterList = new ArrayList<>();
                mapMonsterMap.put(monsterResource.getMap(), monsterList);
            }
            monsterList.add(monsterResource);

            //初始化资源id与怪物资源的映射
            monsterMap.put(monsterResource.getResourceId(), monsterResource);

        }
        logger.debug("加载完成");
    }

    /**
     * 获取对应怪物资源id的怪物资源
     */
    public MonsterResource getResourceById(int resourceId) {
        return monsterMap.get(resourceId);
    }

    public List<MonsterResource> getMonsterList(int map) {
        return mapMonsterMap.get(map);
    }
}
