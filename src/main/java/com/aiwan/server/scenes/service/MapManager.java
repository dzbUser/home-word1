package com.aiwan.server.scenes.service;

import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.mapresource.PositionMeaning;
import com.aiwan.server.scenes.protocol.RoleMessage;
import com.aiwan.server.scenes.protocol.SM_RolesInMap;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

    /** 获取地图 */
    public MapResource getMapResource(Integer mapType){
        return mapResourceMap.get(mapType);
    }

    /**存储在本地图中的用户*/
    private Map<Integer, Map<Long, Role>> roleMap = new ConcurrentHashMap<Integer, Map<Long, Role>>();

    /**
     * 初始化资源
     * */
    public void init(){
        for (Map.Entry<Integer,MapResource> entry:mapResourceMap.entrySet()){
            roleMap.put(entry.getKey(), new ConcurrentHashMap<Long, Role>(36));
        }
    }

    /**  存入地图资源*/
    public void putMapResource(MapResource mapResource){

        mapResourceMap.put(mapResource.getMapType(),mapResource);
    }

    /** 添加用户 */
    public void putRole(Role role) {
        roleMap.get(role.getMap()).put(role.getId(), role);
    }

    /** 获取用户 */
    public Role getUser(Integer mapType, Long rId) {
        return roleMap.get(mapType).get(rId);
    }

    /** 删除用户 */
    public void removeRole(Integer mapType, Long rId) {
        roleMap.get(mapType).remove(rId);
    }

    /** 获取地图信息 */
    public String getMapContent(int x,int y,int mapType){
        //获取地图静态资源
        MapResource mapResource = mapResourceMap.get(mapType);
        StringBuilder stringBuilder = new StringBuilder();
        //添加地图资源
        stringBuilder.append("您所在位子为"+mapResource.getName()+"的("+x+","+y+")\n");

        //创建用户标志
        byte[][] userFlag = new byte[mapResource.getHeight()][mapResource.getWidth()];
        //初始化用户标志
        for (int i = 0;i < mapResource.getHeight();i++) {
            for (int j = 0; j < mapResource.getWidth(); j++) {
                userFlag[i][j] = 0;
            }
        }

        /** 添加用户标志 */
        for (Map.Entry<Long, Role> entry : roleMap.get(mapType).entrySet()) {
            Role role = entry.getValue();
            userFlag[role.getX() - 1][role.getY() - 1] = 1;
        }

        for (int i = 0;i < mapResource.getHeight();i++){
            for (int j = 0;j < mapResource.getWidth();j++){
                if (userFlag[i][j] == 1) {
                    stringBuilder.append("用户 ");
                }

                else{
                    stringBuilder.append(mapResource.getMapMessage()[i][j]+" ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
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
        Map<Long, Role> map = roleMap.get(id);
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
