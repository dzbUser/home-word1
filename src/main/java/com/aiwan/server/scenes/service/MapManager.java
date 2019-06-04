package com.aiwan.server.scenes.service;

import com.aiwan.server.scenes.mapresource.MapResource;
//import com.aiwan.server.scenes.mapresource.PositionMeaning;
//import com.aiwan.server.user.entity.User;
import com.aiwan.server.scenes.mapresource.PositionMeaning;
import com.aiwan.server.user.model.User;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 地图管理
 * @author dengzebiao
 * @since 2019.5.17
 * */
@Component("mapManager")
public class MapManager {
    //存地图
    private Map<Integer, MapResource> mapResourceMap = new ConcurrentHashMap<>();

    //获取地图
    public MapResource getMapResource(Integer mapType){
        return mapResourceMap.get(mapType);
    }

    /**存储在本地图中的用户*/
    private Map<Integer,Map<String, User>> userMap = new ConcurrentHashMap<>();

    /**
     * 初始化资源
     * */
    public void init(){
        for (Map.Entry<Integer,MapResource> entry:mapResourceMap.entrySet()){
            userMap.put(entry.getKey(),new ConcurrentHashMap<String, User>());
        }
    }
    //存入地图资源
    public void putMapResource(MapResource mapResource){

        mapResourceMap.put(mapResource.getMapType(),mapResource);
    }

    //添加用户
    public void putUser(User user){
        userMap.get(user.getMap()).put(user.getAcountId(),user);
    }
    //获取用户
    public User getUser(Integer mapType,String username){
        return userMap.get(mapType).get(username);
    }
    //删除用户
    public void removeUser(Integer mapType,String username){
        userMap.get(mapType).remove(username);
    }

    /** 获取地图信息 */
    public String getMapContent(int x,int y,int mapType){
        //获取地图静态资源
        MapResource mapResource = mapResourceMap.get(mapType);
        StringBuilder stringBuilder = new StringBuilder();
        //添加地图资源
        stringBuilder.append("您所在位子为"+mapResource.getName()+"的("+x+","+y+")\n");

        //创建用户标志
        byte[][] userflag = new byte[mapResource.getHeight()][mapResource.getWidth()];
        //初始化用户标志
        for (int i = 0;i < mapResource.getHeight();i++) {
            for (int j = 0; j < mapResource.getWidth(); j++) {
                userflag[i][j] =0;
            }
        }
        //添加用户标志
        for (Map.Entry<String, User> entry : userMap.get(mapType).entrySet()) {
            User  user  = entry.getValue();
            userflag[user.getCurrentX()-1][user.getCurrentY()-1] = 1;
        }

        for (int i = 0;i < mapResource.getHeight();i++){
            for (int j = 0;j < mapResource.getWidth();j++){
                if (userflag[i][j] == 1)
                    stringBuilder.append("用户 ");
                else
                    stringBuilder.append(mapResource.getMapMessage()[i][j]+" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /** 是否可行走 */
    public boolean allowMove(int x,int y,int mapType){
        MapResource mapResource = mapResourceMap.get(mapType);
        if (x>mapResource.getHeight()||y>mapResource.getWidth())
            return false;
        Map<Integer, PositionMeaning> map = mapResource.getPositionMeaningHashMap();
        if (mapResource.getPositionMeaningHashMap().get(mapResource.getMap()[x-1][y-1]).getAllowMove() == 1){
            return true;
        }
        return false;
    }

    public Map<String,User> getUserMapByMapType(Integer mapType){
        return userMap.get(mapType);
    }

}
