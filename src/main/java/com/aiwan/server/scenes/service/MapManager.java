package com.aiwan.server.scenes.service;

import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.mapresource.PositionMeaning;
import com.aiwan.server.scenes.model.SceneObject;
import com.aiwan.server.scenes.protocol.RoleMessage;
import com.aiwan.server.scenes.protocol.SM_RolesInMap;
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
            sceneMap.put(entry.getKey(), SceneObject.valueOf(entry.getKey()));
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
    public void putRole(Role role) {
        sceneMap.get(role.getMap()).putRole(role);
    }

    /** 获取用户 */
    public Role getUser(Integer mapType, Long rId) {
        return sceneMap.get(mapType).getRole(rId);
    }

    /** 删除用户 */
    public void removeRole(Integer mapType, Long rId) {
        sceneMap.get(mapType).removeRole(rId);
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
        Map<Long, Role> map = sceneMap.get(id).getRoleMap();
        List<RoleMessage> roleMessages = new ArrayList<>();
        //遍历所有用户，添加到角色列表中
        for (Role role : map.values()) {
            //获取session
            roleMessages.add(RoleMessage.valueOf(role.getId(), role.getName(), role.getX(), role.getY()));
        }
        //遍历所有用户，发送消息
        for (Role role : map.values()) {
            Session session = SessionManager.getSessionByAccountId(role.getAccountId());
            session.messageSend(SMToDecodeData.shift(StatusCode.MAPMESSAGE, SM_RolesInMap.valueOf(id, role.getX(), role.getY(), roleMessages)));
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


}
