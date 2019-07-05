package com.aiwan.server.scenes.service;

import com.aiwan.server.monster.model.Monster;
import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.scenes.fight.model.pvpunit.FighterRole;
import com.aiwan.server.scenes.fight.model.pvpunit.RoleBaseMessage;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.mapresource.PositionMeaning;
import com.aiwan.server.scenes.model.SceneObject;
import com.aiwan.server.scenes.protocol.MonsterMessage;
import com.aiwan.server.scenes.protocol.RoleMessage;
import com.aiwan.server.scenes.protocol.SM_MapMessage;
import com.aiwan.server.user.role.player.model.Role;
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
    public void putFighterRole(Role role) {
        sceneMap.get(role.getMap()).putFighterRole(role);
    }


    /** 删除用户 */
    public void removeFighterRole(Integer mapType, Long rId) {
        sceneMap.get(mapType).removeFighterRole(rId);
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
        Map<Long, FighterRole> map = sceneMap.get(id).getFighterRoleMap();
        List<RoleMessage> roleMessages = new ArrayList<>();
        //遍历所有用户，添加到角色列表中
        for (FighterRole fighterRole : map.values()) {
            //获取基础信息
            final RoleBaseMessage roleBaseMessage = fighterRole.getRoleBaseMessage();
            roleMessages.add(RoleMessage.valueOf(roleBaseMessage.getrId(), roleBaseMessage.getName(), roleBaseMessage.getPosition().getX(), roleBaseMessage.getPosition().getY()));
        }

        //遍历所有怪物，获取怪物信息
        Map<Long, Monster> monsterMap = sceneMap.get(id).getMonsterMap();
        List<MonsterMessage> monsterMessages = new ArrayList<>();
        for (Monster monster : monsterMap.values()) {
            monsterMessages.add(MonsterMessage.valueOf(monster.getObjectId(), monster.getResourceId(), monster.getPosition(), monster.getBlood()));
        }

        //遍历所有用户，发送消息
        for (FighterRole fighterRole : map.values()) {
            final RoleBaseMessage roleBaseMessage = fighterRole.getRoleBaseMessage();
            Session session = SessionManager.getSessionByAccountId(roleBaseMessage.getAccountId());
            session.messageSend(SMToDecodeData.shift(StatusCode.MAPMESSAGE, SM_MapMessage.valueOf(id, roleBaseMessage.getPosition().getX(), roleBaseMessage.getPosition().getY(), roleMessages, monsterMessages)));
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
        init();
    }

    /**
     * 获取场景对象
     */
    public SceneObject getSceneObject(int map) {
        return sceneMap.get(map);
    }

}
