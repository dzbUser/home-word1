package com.aiwan.scenes.mapresource;

import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.publicsystem.service.ChannelManager;
import com.aiwan.scenes.protocol.SM_Shift;
import com.aiwan.scenes.service.MapManager;
import com.aiwan.user.entity.User;
import com.aiwan.util.ConsequenceCode;
import com.aiwan.util.GetBean;
import com.aiwan.util.SMToDecodeData;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 地图资源类
 * */
public class MapResource {
    //地图类型
    private int mapType;
    //地图名字
    private String name;
    //地图
    private int[][] map;
    //地图长度
    private int height;
    //地图宽度
    private int width;
    //初始横坐标
    private int originX;
    //初始纵坐标
    private int originY;
    //存储在本地图中的用户
    private Map<String, User> userMap = new HashMap<>();
    //地图动态内容
    private String[][] mapMessage;

    //添加用户
    public void putUser(String username,User user){
        userMap.put(username,user);
    }
    //获取用户
    public User getUser(String username){
        return userMap.get(username);
    }
    //删除用户
    public void removeUser(String username){
        userMap.remove(username);
    }

    public MapResource(int mapType, String name, int[][] map,int height,int width,int originX,int originY) {
        this.mapType = mapType;
        this.name = name;
        this.map = map;
        this.height = height;
        this.width = width;
        this.originX = originX;
        this.originY = originY;
        //初始化动态资源
        initMapMessage();
    }

    //初始化动态资源
    private void initMapMessage(){
        this.mapMessage = new String[height][width];
        MapManager mapManager = GetBean.getMapManager();
        for (int i = 0;i < height;i++){
            for (int j = 0;j < width;j++){
                mapMessage[i][j] = mapManager.getmapProtocol(map[i][j]);
            }
        }
    }
    public int getMapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getOriginX() {
        return originX;
    }

    public void setOriginX(int originX) {
        this.originX = originX;
    }

    public int getOriginY() {
        return originY;
    }

    public void setOriginY(int originY) {
        this.originY = originY;
    }

    //获取地图信息
    public String getMapContent(int x, int y){
        MapManager mapManager = GetBean.getMapManager();
        StringBuilder stringBuilder = new StringBuilder();
        //添加地图资源
        stringBuilder.append("您所在位子为"+name+"的("+x+","+y+")\n");

        //创建用户标志
        byte[][] userflag = new byte[height][width];
        //初始化用户标志
        for (int i = 0;i < height;i++) {
            for (int j = 0; j < width; j++) {
                userflag[i][j] =0;
            }
        }
        //添加用户标志
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            User  user  = entry.getValue();
            userflag[user.getCurrentX()-1][user.getCurrentY()-1] = 1;
        }

        for (int i = 0;i < height;i++){
            for (int j = 0;j < width;j++){
                if (userflag[i][j] == 1)
                    stringBuilder.append("用户 ");
                else
                    stringBuilder.append(mapMessage[i][j]+" ");
            }
            stringBuilder.append("\n");
        }
        //生成地图信息
        return stringBuilder.toString();
    }

    //发送地图信息到本地图所有用户
    public void sendMessage(){
        DecodeData decodeData;
        User user;
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            //获取本地图所有用户
            user = entry.getValue();
            String username = user.getUsername();
            String content = getMapContent(user.getCurrentX(),user.getCurrentY());
            //获取对应用户的channel
            Channel channel = ChannelManager.getChannelByUsername(username);
            //创建跳转类
            SM_Shift sm_shift = new SM_Shift(user.getCurrentX(),user.getCurrentY(),user.getMap(),content);
            decodeData = SMToDecodeData.shift(ConsequenceCode.SHIFTSUCCESS,sm_shift);
            channel.writeAndFlush(decodeData);
        }
    }

    //用户移动
    public void move(String username,int x,int y){
        User user = getUser(username);
        user.setCurrentY(x);
        user.setCurrentY(y);
        putUser(username,user);
    }

    public boolean allowMove(int x,int y){
        if (x>height||y>width)
            return false;
        MapManager mapManager = GetBean.getMapManager();
        if (mapManager.getPositionMeaning(map[x-1][y-1]).getAllowMove() == 1){
            return true;
        }
        return false;
    }
}
