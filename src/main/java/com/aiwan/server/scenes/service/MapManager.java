package com.aiwan.server.scenes.service;

import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.scenes.command.SceneRateCommand;
import com.aiwan.server.scenes.fight.model.pvpunit.BaseUnit;
import com.aiwan.server.scenes.fight.model.pvpunit.FighterRole;
import com.aiwan.server.scenes.fight.model.pvpunit.MonsterUnit;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.mapresource.PositionMeaning;
import com.aiwan.server.scenes.model.SceneObject;
import com.aiwan.server.scenes.protocol.MonsterMessage;
import com.aiwan.server.scenes.protocol.RoleMessage;
import com.aiwan.server.scenes.protocol.SM_MapMessage;
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
    private Map<Integer, MapResource> mapResourceMap = new ConcurrentHashMap<Integer, MapResource>();

    /**存储在本地图中的用户*/
    private Map<Integer, SceneObject> sceneMap = new HashMap<>();

    /**
     * 初始化资源
     * */
    public void init(){
        for (Map.Entry<Integer,MapResource> entry:mapResourceMap.entrySet()){
            SceneObject sceneObject = SceneObject.valueOf(entry.getKey());
            sceneObject.init();
            sceneMap.put(entry.getKey(), sceneObject);
            SceneRateCommand sceneRateCommand = new SceneRateCommand(null, entry.getKey(), 0, 1000);
            GetBean.getSceneExecutorService().submit(sceneRateCommand);
            sceneObject.setAbstractSceneRateCommand(sceneRateCommand);
        }
    }

    /**
     * 获取地图
     */
    public MapResource getMapResource(Integer mapType) {
        return mapResourceMap.get(mapType);
    }

    /**  存入地图资源*/
    public void putMapResource(MapResource mapResource){

        mapResourceMap.put(mapResource.getMapType(),mapResource);
    }

    /** 添加用户 */
    public void putFighterRole(FighterRole fighterRole) {
        sceneMap.get(fighterRole.getMapId()).putBaseUnit(fighterRole);
    }


    /** 删除用户 */
    public void removeFighterRole(Integer mapType, Long rId) {
        sceneMap.get(mapType).removeBaseUnit(rId);
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
    public void sendMessageToUsers(int id) {
        //获取地图中的所有用户
        Map<Long, BaseUnit> map = sceneMap.get(id).getBaseUnitMap();
        List<RoleMessage> roleMessages = new ArrayList<>();
        List<MonsterMessage> monsterMessages = new ArrayList<>();
        //遍历所有用户，添加到角色列表中
        for (BaseUnit baseUnit : map.values()) {
            //获取基础信息
            if (baseUnit.isMonster()) {
                MonsterUnit monsterUnit = (MonsterUnit) baseUnit;
                monsterMessages.add(MonsterMessage.valueOf(monsterUnit.getId(), monsterUnit.getResourceId(), monsterUnit.getPosition(), monsterUnit.getHp()));
            } else {
                FighterRole fighterRole = (FighterRole) baseUnit;
                roleMessages.add(RoleMessage.valueOf(fighterRole.getId(), fighterRole.getName(), fighterRole.getPosition().getX(), fighterRole.getPosition().getY(), fighterRole.getHp(), fighterRole.getMp(), fighterRole.getLevel()));
            }
        }
        //遍历所有用户，发送消息
        for (BaseUnit baseUnit : map.values()) {
            if (!baseUnit.isMonster()) {
                FighterRole fighterRole = (FighterRole) baseUnit;
                Session session = SessionManager.getSessionByAccountId(fighterRole.getAccountId());
                SM_MapMessage sm_mapMessage = SM_MapMessage.valueOf(id, fighterRole.getPosition().getX(), fighterRole.getPosition().getY(), roleMessages, monsterMessages, fighterRole.getHp(), fighterRole.getMp());
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
    }

    /**
     * 获取场景对象
     */
    public SceneObject getSceneObject(int map) {
        return sceneMap.get(map);
    }

}
