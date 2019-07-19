package com.aiwan.server.world.scenes.service;

import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.fight.protocol.SM_UnitStatusMessage;
import com.aiwan.server.user.role.fight.pvpunit.BaseUnit;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.world.base.scene.AbstractScene;
import com.aiwan.server.world.scenes.mapresource.MapResource;
import com.aiwan.server.world.scenes.mapresource.PositionMeaning;
import com.aiwan.server.world.base.scene.UniqueScene;
import com.aiwan.server.world.scenes.protocol.SM_MapMessage;
import com.aiwan.server.world.scenes.protocol.UnitMessage;
import com.aiwan.server.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 地图管理
 * @author dengzebiao
 * @since 2019.5.17
 * */
@Manager
public class MapManager {

    Logger logger = LoggerFactory.getLogger(MapManager.class);
    /** 地图资源 */
    @Static(initializeMethodName = "initMapResource")
    private Map<Integer, MapResource> mapResourceMap = new HashMap<>();

    private Map<Integer, Map<Integer, AbstractScene>> sceneContainer = new ConcurrentHashMap<>();
    /**
     * 初始化资源
     * */
    public void init(){
        for (Map.Entry<Integer,MapResource> entry:mapResourceMap.entrySet()){
            if (entry.getValue().getIsDungeon() == 1) {
                continue;
            }
            UniqueScene uniqueScene = UniqueScene.valueOf(entry.getKey());
            uniqueScene.init();
            putSceObject(uniqueScene);
        }
    }

    /**
     * 获取地图
     */
    public MapResource getMapResource(Integer mapType) {
        return mapResourceMap.get(mapType);
    }


    /**  存入地图资源*/
    public void putMapResource(MapResource mapResource) {

        mapResourceMap.put(mapResource.getMapId(), mapResource);
    }


    public void removeFighterRole(Integer mapId, int sceneId, Long rId) {
        sceneContainer.get(mapId).get(sceneId).removeBaseUnit(rId);
    }

    /** 是否可行走 */
    public boolean allowMove(int x,int y,int mapType){
        MapResource mapResource = mapResourceMap.get(mapType);
        if (x < 1 || y < 1 || x > mapResource.getHeight() || y > mapResource.getWidth())
        {
            return false;
        }
        Map<Integer, PositionMeaning> map = mapResource.getPositionMeaningHashMap();
        if (mapResource.getPositionMeaningHashMap().get(mapResource.getMap()[x-1][y-1]).getAllowMove() == 1){
            return true;
        }
        return false;
    }

    /** 地图内所有用户发送地图消息 */
    public void sendMessageToUsers(int mapId, int sceneId) {
        //获取地图中的所有用户
        AbstractScene abstractScene = sceneContainer.get(mapId).get(sceneId);
        Map<Long, BaseUnit> map = abstractScene.getBaseUnitMap();
        List<UnitMessage> list = new ArrayList<>();
        //遍历所有用户，添加到角色列表中
        for (BaseUnit baseUnit : map.values()) {
            //获取基础信息
            if (baseUnit.isDeath()) {
                continue;
            }
            list.add(UnitMessage.valueOf(baseUnit.getId(), baseUnit.getPosition().getX(), baseUnit.getPosition().getY(), baseUnit.getName()));
        }
        //遍历所有用户，发送消息
        for (BaseUnit baseUnit : map.values()) {
            if (!baseUnit.isMonster()) {
                RoleUnit roleUnit = (RoleUnit) baseUnit;
                Session session = SessionManager.getSessionByAccountId(roleUnit.getAccountId());
                SM_MapMessage sm_mapMessage = SM_MapMessage.valueOf(abstractScene.getMapId(), list, roleUnit.getPosition().getX(), roleUnit.getPosition().getY());
                session.messageSend(SMToDecodeData.shift(StatusCode.MAPMESSAGE, sm_mapMessage));
            }
        }

    }

    /**
     * 初始化地图静态资源
     */
    private void initMapResource() throws InstantiationException, IllegalAccessException {
        //获取地图资源静态路径
        String path = ResourceUtil.getResourcePath(MapResource.class);
        //加载资源
        logger.debug("开始加载地图静态资源");
        List<MapResource> list = ExcelUtil.analysisWithRelativePath(path, MapResource.class);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).init();
            putMapResource(list.get(i));
        }
        logger.debug("开始加载地图静态资源完成");
    }

    /**
     * 获取场景对象
     */
    public AbstractScene getSceneObject(int map, int sceneId) {
        if (sceneContainer.get(map) == null) {
            return null;
        }
        return sceneContainer.get(map).get(sceneId);
    }

    /**
     * 存入地图对象
     */
    public void putSceObject(AbstractScene abstractScene) {
        if (sceneContainer.get(abstractScene.getMapId()) == null) {
            sceneContainer.put(abstractScene.getMapId(), new ConcurrentHashMap<>());
        }
        sceneContainer.get(abstractScene.getMapId()).put(abstractScene.getSceneId(), abstractScene);
    }

    /**
     * 删除
     */
    public void removeSceObject(int mapId, int sceneId) {
        sceneContainer.get(mapId).remove(sceneId);
    }

    /**
     * 发送战斗单位状态改变信息
     *
     * @param unit 战斗单位
     */
    public void synUnitStatusMessage(BaseUnit unit) {
        AbstractScene abstractScene = getSceneObject(unit.getMapId(), unit.getSceneId());
        SM_UnitStatusMessage sm_unitStatusMessage = SM_UnitStatusMessage.valueOf(unit.getId(), unit.getName(), unit.getMaxHp(), unit.getHp());
        for (BaseUnit baseUnit : abstractScene.getBaseUnitMap().values()) {
            if (!baseUnit.isMonster()) {
                SessionManager.sendMessageByRid(baseUnit.getId(), StatusCode.UnitStatus, sm_unitStatusMessage);
            }
        }
    }

}
